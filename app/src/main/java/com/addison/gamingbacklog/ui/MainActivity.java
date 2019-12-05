package com.addison.gamingbacklog.ui;

import android.os.Bundle;

import com.addison.gamingbacklog.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupNavigation();

        initializeAds();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.mainNavigationFragment).navigateUp();
    }

    private void setupNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.mainNavigationFragment);

        NavigationUI.setupWithNavController(navView, navController);
    }

    private void initializeAds() {
        MobileAds.initialize(this);

        AdRequest adRequest = new AdRequest.Builder().build();

        AdView adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);
    }
}
