package com.addison.gamingbacklog.repository;

import static android.content.Context.MODE_PRIVATE;

import static com.addison.gamingbacklog.widget.PlayingWidgetProvider.PLAYING_PREFERENCE_KEY;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.addison.gamingbacklog.BuildConfig;
import com.addison.gamingbacklog.R;
import com.addison.gamingbacklog.repository.database.GameEntry;
import com.addison.gamingbacklog.repository.database.LibraryDatabase;
import com.addison.gamingbacklog.repository.service.models.Game;
import com.addison.gamingbacklog.repository.service.IGDBService;
import com.addison.gamingbacklog.repository.service.RequestStatus;
import com.addison.gamingbacklog.repository.service.models.Video;
import com.addison.gamingbacklog.widget.PlayingWidgetProvider;

import java.util.List;

public class Repository {

    private static final Object LOCK = new Object();
    private static Repository sInstance;

    private final IGDBService mIGDBService;
    private final LibraryDatabase mLibraryDatabase;

    private Repository(Context context) {
        mIGDBService = new IGDBService();
        mLibraryDatabase = LibraryDatabase.getInstance(context);
    }

    public static Repository getInstance(Context context) {
        synchronized (LOCK) {
            if (sInstance == null) {
                sInstance = new Repository(context);
            }
        }
        return sInstance;
    }

    public void retrieveGames() {
        mIGDBService.retrieveGames();
    }

    public void retrieveGameVideos(Integer gameId) {
        mIGDBService.retrieveGameVideos(gameId);
    }

    public LiveData<RequestStatus> getRequestGamesStatus() {
        return mIGDBService.getRequestGamesStatus();
    }

    public LiveData<RequestStatus> getRequestVideosStatus() {
        return mIGDBService.getRequestVideosStatus();
    }

    public LiveData<List<Game>> getGamesList() {
        return mIGDBService.getGamesList();
    }

    public LiveData<List<Video>> getGameVideosList() {
        return mIGDBService.getGameVideosList();
    }

    public LiveData<List<GameEntry>> getPlayingGames() {
        return mLibraryDatabase.libraryDao().getPlayingGames();
    }

    public LiveData<List<GameEntry>> getBeatenGames() {
        return mLibraryDatabase.libraryDao().getBeatenGames();
    }

    public LiveData<List<GameEntry>> getSavedGames() {
        return mLibraryDatabase.libraryDao().getSavedGames();
    }

    public LiveData<GameEntry> getGame(Integer id) {
        return mLibraryDatabase.libraryDao().getGame(id);
    }

    public void addGameToLibrary(final GameEntry gameEntry) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mLibraryDatabase.libraryDao().insertGame(gameEntry);
                return null;
            }
        }.execute();
    }

    public void removeGameFromLibrary(final GameEntry gameEntry) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mLibraryDatabase.libraryDao().deleteGame(gameEntry);
                return null;
            }
        }.execute();
    }

    public void updateWidget(Context context, List<GameEntry> games) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(PLAYING_PREFERENCE_KEY, convertToString(
                        games, context.getString(R.string.no_games)))
                .apply();

        ComponentName provider = new ComponentName(context, PlayingWidgetProvider.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] ids = appWidgetManager.getAppWidgetIds(provider);
        PlayingWidgetProvider playingWidgetProvider = new PlayingWidgetProvider();
        playingWidgetProvider.onUpdate(context, appWidgetManager, ids);
    }

    private String convertToString(List<GameEntry> gamesList, String defaultMessage) {
        String games = "";
        if (gamesList != null && !gamesList.isEmpty()) {
            for (GameEntry gameEntry : gamesList) {
                games = games.concat(String.format("- %s\n", gameEntry.getName()));
            }
        } else {
            games = defaultMessage;
        }
        return  games;
    }
}
