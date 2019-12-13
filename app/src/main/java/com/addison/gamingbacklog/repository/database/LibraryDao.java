package com.addison.gamingbacklog.repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LibraryDao {

    @Query("SELECT * FROM library")
    LiveData<List<GameEntry>> getGames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(GameEntry gameEntry);

    @Delete
    void deleteGame(GameEntry gameEntry);
}
