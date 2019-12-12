package com.addison.gamingbacklog.ui.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.addison.gamingbacklog.GamingBacklogApplication;
import com.addison.gamingbacklog.databinding.ActivityDetailsBinding;
import com.addison.gamingbacklog.repository.service.models.Video;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.widget.Toast;

import com.addison.gamingbacklog.R;

import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity implements VideosAdapter.VideosAdapterOnClickHandler {

    public static final String EXTRA_GAME = "game";

    private ActivityDetailsBinding mActivityDetailsBinding;
    private VideosAdapter mVideosAdapter;

    private GameUi mGameUi;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        mActivityDetailsBinding.setLifecycleOwner(this);

        if (getIntent().hasExtra(EXTRA_GAME)) {
            mGameUi = getIntent().getParcelableExtra(EXTRA_GAME);
            populateUi(mGameUi);
        } else {
            Toast.makeText(this, getString(R.string.details_error),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        mActivityDetailsBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        mActivityDetailsBinding.adView.loadAd(adRequest);
    }

    private void populateUi(GameUi gameUi) {
        if (gameUi.getCoverUrl() != null) {
            Glide.with(this.getApplicationContext())
                    .load(gameUi.getCoverUrl())
                    .centerCrop()
                    .into(mActivityDetailsBinding.contentDetails.ivGameCover);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(gameUi.getFirstReleaseDate() * 1000);
        mActivityDetailsBinding.contentDetails.tvGameName.setText(
                gameUi.getName());
        mActivityDetailsBinding.contentDetails.tvGameRelease.setText(
                String.valueOf(calendar.get(Calendar.YEAR)));
        mActivityDetailsBinding.contentDetails.tvGameSummary.setText(
                gameUi.getSummary());
    }
}
