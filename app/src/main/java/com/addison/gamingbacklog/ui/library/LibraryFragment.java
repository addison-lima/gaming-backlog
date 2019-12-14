package com.addison.gamingbacklog.ui.library;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addison.gamingbacklog.R;
import com.addison.gamingbacklog.databinding.FragmentLibraryBinding;
import com.addison.gamingbacklog.repository.Repository;
import com.addison.gamingbacklog.repository.database.GameEntry;
import com.addison.gamingbacklog.ui.details.DetailsActivity;
import com.addison.gamingbacklog.ui.details.GameUi;

import java.util.List;

public class LibraryFragment extends Fragment implements
        LibraryAdapter.LibraryAdapterOnClickHandler {

    FragmentLibraryBinding mFragmentLibraryBinding;
    private LibraryAdapter mLibraryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repository.getInstance(getContext()).getSavedGames().observe(this, getSavedGamesObserver());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentLibraryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_library,
                container, false);
        mFragmentLibraryBinding.setLifecycleOwner(this);

        mLibraryAdapter = new LibraryAdapter(getContext(), this);
        mFragmentLibraryBinding.rvSaved.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentLibraryBinding.rvSaved.setAdapter(mLibraryAdapter);

        return mFragmentLibraryBinding.getRoot();
    }

    @Override
    public void onClick(GameEntry gameEntry) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_GAME, convert(gameEntry));
        startActivity(intent);
    }

    private Observer<List<GameEntry>> getSavedGamesObserver() {
        return new Observer<List<GameEntry>>() {
            @Override
            public void onChanged(List<GameEntry> gameEntries) {
                boolean isEmpty = (gameEntries == null) || gameEntries.isEmpty();
                mFragmentLibraryBinding.tvEmptyMessage.setVisibility(
                        isEmpty ? View.VISIBLE : View.GONE);
                mFragmentLibraryBinding.rvSaved.setVisibility(
                        isEmpty ? View.GONE : View.VISIBLE);
                mLibraryAdapter.setData(gameEntries);
            }
        };
    }

    private GameUi convert(GameEntry gameEntry) {
        return new GameUi(
                gameEntry.getId(),
                gameEntry.getCoverUrl(),
                gameEntry.getFirstReleaseDate(),
                gameEntry.getName(),
                gameEntry.getSummary());
    }
}
