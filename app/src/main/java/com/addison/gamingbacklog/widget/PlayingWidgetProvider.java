package com.addison.gamingbacklog.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.addison.gamingbacklog.BuildConfig;
import com.addison.gamingbacklog.R;
import com.addison.gamingbacklog.ui.MainActivity;

public class PlayingWidgetProvider extends AppWidgetProvider {

    public static final String PLAYING_PREFERENCE_KEY = "playing_preference_key";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        String games = sharedPreferences.getString(
                PLAYING_PREFERENCE_KEY, context.getString(R.string.no_games));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_playing);
        remoteViews.setOnClickPendingIntent(R.id.tv_widget_playing_games, pendingIntent);
        remoteViews.setTextViewText(R.id.tv_widget_playing_games, games);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}
