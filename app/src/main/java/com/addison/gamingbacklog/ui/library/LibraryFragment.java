package com.addison.gamingbacklog.ui.library;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.addison.gamingbacklog.repository.Repository;
import com.addison.gamingbacklog.ui.base.GameFragment;

public class LibraryFragment extends GameFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repository.getInstance(getContext()).getSavedGames().observe(this, getSavedGamesObserver());
    }
}
