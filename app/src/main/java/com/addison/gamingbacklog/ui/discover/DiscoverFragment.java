package com.addison.gamingbacklog.ui.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.addison.gamingbacklog.R;
import com.addison.gamingbacklog.repository.service.Game;
import com.addison.gamingbacklog.repository.service.RequestStatus;

import java.util.List;

public class DiscoverFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        DiscoverViewModel discoverViewModel = ViewModelProviders.of(this).get(
                DiscoverViewModel.class);

        discoverViewModel.getRequestStatus().observe(this, getRequestStatusObserver());
        discoverViewModel.getGamesList().observe(this, getGamesListObserver());

        return inflater.inflate(R.layout.fragment_discover, container, false);
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
        View view = getView();
        if (view != null) {
            view.findViewById(R.id.tv_failure_message)
                    .setVisibility(requestState.equals(RequestStatus.RequestState.FAILURE)
                            ? View.VISIBLE : View.INVISIBLE);
            view.findViewById(R.id.pb_loading_indicator)
                    .setVisibility(requestState.equals(RequestStatus.RequestState.LOADING)
                            ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
