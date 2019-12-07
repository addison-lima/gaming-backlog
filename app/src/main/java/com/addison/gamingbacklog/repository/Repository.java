package com.addison.gamingbacklog.repository;

import androidx.lifecycle.LiveData;

import com.addison.gamingbacklog.repository.service.Game;
import com.addison.gamingbacklog.repository.service.IGDBService;
import com.addison.gamingbacklog.repository.service.RequestStatus;

import java.util.List;

public class Repository {

    private static final Object LOCK = new Object();
    private static Repository sInstance;

    private final IGDBService mIGDBService;

    private Repository() {
        mIGDBService = new IGDBService();
    }

    public static Repository getInstance() {
        synchronized (LOCK) {
            if (sInstance == null) {
                sInstance = new Repository();
            }
        }
        return sInstance;
    }

    public void retrieveGames() {
        mIGDBService.retrieveGames();
    }

    public LiveData<RequestStatus> getRequestStatus() {
        return mIGDBService.getRequestStatus();
    }

    public LiveData<List<Game>> getGamesList() {
        return mIGDBService.getGamesList();
    }
}
