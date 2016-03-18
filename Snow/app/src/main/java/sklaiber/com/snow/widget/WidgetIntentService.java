package sklaiber.com.snow.widget;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.RemoteViews;
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortColums;
import sklaiber.com.snow.database.ResortProvider;
import sklaiber.com.snow.ui.main.MainActivity;

public class WidgetIntentService extends IntentService {

  public WidgetIntentService() {
    super("WidgetIntentService");
  }

  @Override protected void onHandleIntent(Intent intent) {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, Widget.class));

    Cursor cursor =
        getContentResolver().query(ResortProvider.Resorts.CONTENT_URI, null, null, null, null);

    if (cursor == null) {
      return;
    }

    if (!cursor.moveToFirst()) {
      cursor.close();
      return;
    }

    for (int appWidgetId : appWidgetIds) {
      RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.widget);
      views.setTextViewText(R.id.widget_resort, cursor.getString(cursor.getColumnIndexOrThrow(ResortColums.NAME)));
      views.setTextViewText(R.id.widget_conditions,
          cursor.getString(cursor.getColumnIndexOrThrow(ResortColums.CONDITIONS)));
      views.setTextViewText(R.id.widget_new_snow, getString(R.string.snow,
          cursor.getString(cursor.getColumnIndexOrThrow(ResortColums.NEW_SNOW))));

      Intent launchIntent = new Intent(this, MainActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
      views.setOnClickPendingIntent(R.id.widget, pendingIntent);

      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    cursor.close();
  }

  private int getWidgetWidth(AppWidgetManager appWidgetManager, int appWidgetId) {
    // Prior to Jelly Bean, widgets were always their default size
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
      return getResources().getDimensionPixelSize(R.dimen.widget_default_width);
    }
    // For Jelly Bean and higher devices, widgets can be resized - the current size can be
    // retrieved from the newly added App Widget Options
    return getWidgetWidthFromOptions(appWidgetManager, appWidgetId);
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private int getWidgetWidthFromOptions(AppWidgetManager appWidgetManager, int appWidgetId) {
    Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
    if (options.containsKey(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)) {
      int minWidthDp = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
      // The width returned is in dp, but we'll convert it to pixels to match the other widths
      DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
      return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minWidthDp,
          displayMetrics);
    }
    return getResources().getDimensionPixelSize(R.dimen.widget_default_width);
  }
}
