package com.addison.gamingbacklog.ui.discover;

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

import com.addison.gamingbacklog.R;
import com.addison.gamingbacklog.databinding.FragmentDiscoverBinding;
import com.addison.gamingbacklog.repository.service.Game;
import com.addison.gamingbacklog.repository.service.RequestStatus;
import com.addison.gamingbacklog.ui.MainActivity;

import java.util.List;
import java.util.Objects;

public class DiscoverFragment extends Fragment {

    private FragmentDiscoverBinding mFragmentDiscoverBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DiscoverViewModel discoverViewModel = ViewModelProviders.of(this)
                .get(DiscoverViewModel.class);

        discoverViewModel.getRequestStatus().observe(this, getRequestStatusObserver());
        discoverViewModel.getGamesList().observe(this, getGamesListObserver());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mFragmentDiscoverBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover,
                container, false);
        mFragmentDiscoverBinding.setLifecycleOwner(this);

        return mFragmentDiscoverBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        trackScreen("onResume Discover");
    }

    private Observer<RequestStatus> getRequestStatusObserver() {
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
    }

    private void trackScreen(String screenName) {
        ((MainActivity) Objects.requireNonNull(getActivity())).trackScreen(screenName);
    }
}
