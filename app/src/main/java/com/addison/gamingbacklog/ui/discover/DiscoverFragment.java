package com.addison.gamingbacklog.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.addison.gamingbacklog.R;
import com.addison.gamingbacklog.databinding.FragmentDiscoverBinding;
import com.addison.gamingbacklog.repository.service.models.Game;
import com.addison.gamingbacklog.repository.service.RequestStatus;
import com.addison.gamingbacklog.ui.MainActivity;
import com.addison.gamingbacklog.ui.details.DetailsActivity;
import com.addison.gamingbacklog.ui.details.GameUi;

import java.util.List;
import java.util.Objects;

public class DiscoverFragment extends Fragment implements DiscoverAdapter.DiscoverAdapterOnClickHandler {

    private FragmentDiscoverBinding mFragmentDiscoverBinding;
    private DiscoverAdapter mDiscoverAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DiscoverViewModel discoverViewModel = ViewModelProviders.of(this)
                .get(DiscoverViewModel.class);

        discoverViewModel.getRequestGamesStatus().observe(this, getRequestGamesStatusObserver());
        discoverViewModel.getGamesList().observe(this, getGamesListObserver());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mFragmentDiscoverBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover,
                container, false);
        mFragmentDiscoverBinding.setLifecycleOwner(this);

        mDiscoverAdapter = new DiscoverAdapter(getContext(), this);
        mFragmentDiscoverBinding.rvDiscover.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentDiscoverBinding.rvDiscover.setAdapter(mDiscoverAdapter);

        return mFragmentDiscoverBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        trackScreen("onResume Discover");
    }

    @Override
    public void onClick(Game game) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_GAME, convert(game));
        startActivity(intent);
    }

    private Observer<RequestStatus> getRequestGamesStatusObserver() {
        return new Observer<RequestStatus>() {
            @Override
            public void onChanged(RequestStatus requestStatus) {
                if (requestStatus != null) {
                    updateUi(requestStatus.getRequestState());
                }
            }
        };
    }

    private Observer<List<Game>> getGamesListObserver() {
        return new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                mDiscoverAdapter.setData(games);
            }
        };
    }

    private void updateUi(RequestStatus.RequestState requestState) {
        mFragmentDiscoverBinding.tvFailureMessage
                .setVisibility(requestState.equals(RequestStatus.RequestState.FAILURE)
                        ? View.VISIBLE : View.INVISIBLE);
        mFragmentDiscoverBinding.pbLoadingIndicator
                .setVisibility(requestState.equals(RequestStatus.RequestState.LOADING)
                        ? View.VISIBLE : View.INVISIBLE);
        mFragmentDiscoverBinding.rvDiscover
                .setVisibility(requestState.equals(RequestStatus.RequestState.SUCCESS)
                        ? View.VISIBLE : View.INVISIBLE);
    }

    private void trackScreen(String screenName) {
        ((MainActivity) Objects.requireNonNull(getActivity())).trackScreen(screenName);
    }

    private GameUi convert(Game game) {
        return new GameUi(
                game.getId(),
                (game.getCover() != null) ? game.getCover().getUrl() : null,
                game.getFirstReleaseDate(),
                game.getName(),
                game.getSummary());
    }
}
