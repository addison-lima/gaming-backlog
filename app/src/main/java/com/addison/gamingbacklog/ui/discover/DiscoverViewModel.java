package com.addison.gamingbacklog.ui.discover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.addison.gamingbacklog.repository.Repository;
import com.addison.gamingbacklog.repository.service.Game;
import com.addison.gamingbacklog.repository.service.RequestStatus;

import java.util.List;

public class DiscoverViewModel extends AndroidViewModel {

    public DiscoverViewModel(@NonNull Application application) {
        super(application);
        Repository.getInstance().retrieveGames();
    }

    public LiveData<RequestStatus> getRequestStatus() {
        return Repository.getInstance().getRequestStatus();
    }

    public LiveData<List<Game>> getGamesList() {
        return Repository.getInstance().getGamesList();
    }
}
