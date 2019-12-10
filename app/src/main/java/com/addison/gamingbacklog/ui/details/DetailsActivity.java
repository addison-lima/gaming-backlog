package com.addison.gamingbacklog.ui.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.addison.gamingbacklog.GamingBacklogApplication;
import com.addison.gamingbacklog.repository.service.models.Video;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.addison.gamingbacklog.R;

public class DetailsActivity extends AppCompatActivity implements VideosAdapter.VideosAdapterOnClickHandler {

    private Tracker mTracker;
    private VideosAdapter mVideosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mVideosAdapter = new VideosAdapter(this);

        initializeTracker();

        initializeAds();
    }

    @Override
    public void onResume() {
        super.onResume();
        trackScreen("onResume Details");
    }

    @Override
    public void onClick(Video video) {
        try {
            Intent appIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(video.getAppIntent()));
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(video.getWebIntent()));
            startActivity(webIntent);
        }
    }

    private void initializeTracker() {
        GamingBacklogApplication application = (GamingBacklogApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    private void trackScreen(String screenName) {
        if (mTracker != null) {
            mTracker.setScreenName(screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    private void initializeAds() {
        MobileAds.initialize(this);

        AdRequest adRequest = new AdRequest.Builder().build();

        AdView adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);
    }
}
