package sklaiber.com.snow.ui.detail;

import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Map;
import sklaiber.com.snow.GeofenceErrorMessages;
import sklaiber.com.snow.GeofenceTransitionsIntentService;
import sklaiber.com.snow.R;
import sklaiber.com.snow.utils.Constants;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    ResultCallback<Status> {

  GoogleApiClient mGoogleApiClient;
  Location mLastLocation;
  protected ArrayList<Geofence> mGeofenceList;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(getIntent().getStringExtra(getString(R.string.key_intent_name)));
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    mGeofenceList = new ArrayList<Geofence>();

    populateGeofenceList();

    buildGoogleApiClient();
  }

  @Override public void onResult(@NonNull Status status) {
    if (status.isSuccess()) {
      Toast.makeText(
          this,
          "Geofences Added",
          Toast.LENGTH_SHORT
      ).show();
    } else {
      String errorMessage = GeofenceErrorMessages.getErrorString(this,
          status.getStatusCode());
      Toast.makeText(DetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
  }

  protected synchronized void buildGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
  }

  private PendingIntent getGeofencePendingIntent() {
    Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
    return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
  }


  public void addGeofencesButtonHandler() {
    if (!mGoogleApiClient.isConnected()) {
      Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
      return;
    }

    try {
      LocationServices.GeofencingApi.addGeofences(
          mGoogleApiClient,
          getGeofencingRequest(),
          getGeofencePendingIntent()
      ).setResultCallback(this);
    } catch (SecurityException securityException) {
      Timber.d(securityException.getMessage());
    }
  }

  public void populateGeofenceList() {
    for (Map.Entry<String, LatLng> entry : Constants.RESORT_LANDMARKS.entrySet()) {

      mGeofenceList.add(new Geofence.Builder()
          .setRequestId(entry.getKey())
          .setCircularRegion(
              entry.getValue().latitude,
              entry.getValue().longitude,
              Constants.GEOFENCE_RADIUS_IN_METERS
          )
          .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
          .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
              Geofence.GEOFENCE_TRANSITION_EXIT)
          .build());
    }
  }

  @Override protected void onStart() {
    super.onStart();
    if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected()) {
      mGoogleApiClient.connect();
    }
  }

  @Override protected void onStop() {
    super.onStop();
    if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.meun_detail, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      case R.id.action_info:
        FragmentManager fm = getSupportFragmentManager();
        InfoFragment infoFragment = InfoFragment.newInstance(getApplicationContext(),
            getIntent().getStringExtra(getString(R.string.key_intent_name)));
        infoFragment.show(fm, "fragment_info");
        return true;
      case R.id.action_geofences:
        addGeofencesButtonHandler();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onConnected(@Nullable Bundle bundle) {
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    if (mLastLocation != null) {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.detail_fragment_container,
          DetailFragment.newInstance(getApplicationContext(), getIntent().getStringExtra(getString(R.string.key_intent_name)),
              getIntent().getFloatExtra(getString(R.string.key_intent_latitude), 0),
              getIntent().getFloatExtra(getString(R.string.key_intent_longitude), 0),
              mLastLocation.getLatitude(), mLastLocation.getLongitude()));
      ft.commit();
    } else {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.detail_fragment_container,
          DetailFragment.newInstance(getApplicationContext(), getIntent().getStringExtra(getString(R.string.key_intent_name)),
              getIntent().getFloatExtra(getString(R.string.key_intent_latitude), 0),
              getIntent().getFloatExtra(getString(R.string.key_intent_longitude), 0)));
      ft.commit();
    }
  }

  @Override public void onConnectionSuspended(int i) {
    mGoogleApiClient.connect();
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Timber.d(connectionResult.getErrorMessage());
  }

  private GeofencingRequest getGeofencingRequest() {
    GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
    builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
    builder.addGeofences(mGeofenceList);
    return builder.build();
  }
}
