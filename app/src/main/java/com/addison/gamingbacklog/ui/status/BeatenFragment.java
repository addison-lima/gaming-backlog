package com.addison.gamingbacklog.ui.status;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.addison.gamingbacklog.repository.Repository;
import com.addison.gamingbacklog.ui.base.GameFragment;

public class BeatenFragment extends GameFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repository.getInstance(getContext()).getBeatenGames().observe(this, getSavedGamesObserver());
    }
}
