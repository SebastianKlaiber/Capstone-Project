package sklaiber.com.snow.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by sklaiber on 18.03.16.
 */
public class Widget extends AppWidgetProvider {
  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    context.startService(new Intent(context, WidgetIntentService.class));
  }

  @Override
  public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId, Bundle newOptions) {
    context.startService(new Intent(context, WidgetIntentService.class));
  }
}
