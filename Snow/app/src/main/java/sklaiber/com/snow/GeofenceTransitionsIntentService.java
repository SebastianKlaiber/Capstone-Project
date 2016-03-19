package sklaiber.com.snow;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import java.util.ArrayList;
import java.util.List;
import sklaiber.com.snow.ui.detail.DetailActivity;
import timber.log.Timber;

public class GeofenceTransitionsIntentService extends IntentService {

  public GeofenceTransitionsIntentService() {
    super(GeofenceTransitionsIntentService.class.getSimpleName());
  }

  @Override
  public void onCreate()
  {
    super.onCreate();
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
    if (geofencingEvent.hasError()) {
      String errorMessage = GeofenceErrorMessages.getErrorString(this,
          geofencingEvent.getErrorCode());
      Timber.d(errorMessage);
      return;
    }

    int geofenceTransition = geofencingEvent.getGeofenceTransition();

    if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
        geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

      List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

      String geofenceTransitionDetails = getGeofenceTransitionDetails(
          this,
          geofenceTransition,
          triggeringGeofences
      );

      sendNotification(geofenceTransitionDetails);
      Timber.i(geofenceTransitionDetails);
    } else {
      Timber.e(getString(R.string.geofence_transition_invalid_type, geofenceTransition));
    }
  }

  private String getGeofenceTransitionDetails(
      Context context,
      int geofenceTransition,
      List<Geofence> triggeringGeofences) {

    String geofenceTransitionString = getTransitionString(geofenceTransition);

    ArrayList triggeringGeofencesIdsList = new ArrayList();
    for (Geofence geofence : triggeringGeofences) {
      triggeringGeofencesIdsList.add(geofence.getRequestId());
    }
    String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

    return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
  }

  private String getTransitionString(int transitionType) {
    switch (transitionType) {
      case Geofence.GEOFENCE_TRANSITION_ENTER:
        return getString(R.string.geofence_transition_entered);
      case Geofence.GEOFENCE_TRANSITION_EXIT:
        return getString(R.string.geofence_transition_exited);
      default:
        return getString(R.string.unknown_geofence_transition);
    }
  }

  private void sendNotification(String notificationDetails) {

    Intent notificationIntent = new Intent(getApplicationContext(), DetailActivity.class);

    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

    stackBuilder.addParentStack(DetailActivity.class);

    stackBuilder.addNextIntent(notificationIntent);

    PendingIntent notificationPendingIntent =
        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

    builder.setSmallIcon(R.mipmap.ic_launcher)
        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
            R.mipmap.ic_launcher))
        .setColor(Color.RED)
        .setContentTitle(notificationDetails)
        .setContentText(getString(R.string.geofence_transition_notification_text))
        .setContentIntent(notificationPendingIntent);

    builder.setAutoCancel(true);

    NotificationManager mNotificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    mNotificationManager.notify(0, builder.build());
  }
}
