package com.addison.gamingbacklog.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.addison.gamingbacklog.GamingBacklogApplication;
import com.addison.gamingbacklog.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupNavigation();

        initializeTracker();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.mainNavigationFragment).navigateUp();
    }

    public void trackScreen(String screenName) {
        if (mTracker != null) {
            mTracker.setScreenName(screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    private void setupNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this,
                R.id.mainNavigationFragment);

        NavigationUI.setupWithNavController(navView, navController);
    }

    private void initializeTracker() {
        GamingBacklogApplication application = (GamingBacklogApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }
}
