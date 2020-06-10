package by.popkov.homework9.widget_weather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import by.popkov.homework9.R;

public class WeatherWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i = 0; i < appWidgetIds.length; i++) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_weather);
            remoteViews.setTextViewText(R.id.textViewTest, "HELLO TEST");
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }

    }
}
