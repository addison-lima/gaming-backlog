package com.addison.gamingbacklog.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GameEntry.class}, version = 1, exportSchema = false)
public abstract class LibraryDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "gameLibrary";
    private static LibraryDatabase sInstance;

    public static LibraryDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        LibraryDatabase.class, LibraryDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract LibraryDao libraryDao();
}
