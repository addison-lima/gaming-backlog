package com.addison.gamingbacklog;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class GamingBacklogApplication extends Application {

    private static GoogleAnalytics sGoogleAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        sGoogleAnalytics = GoogleAnalytics.getInstance(this);
    }

    synchronized public Tracker getDefaultTracker() {
        if (sTracker == null) {
            sTracker = sGoogleAnalytics.newTracker(R.xml.global_tracker);
            sTracker.enableAdvertisingIdCollection(true);
        }
        return sTracker;
    }
}
