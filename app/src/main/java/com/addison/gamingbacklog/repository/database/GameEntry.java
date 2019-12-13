package com.addison.gamingbacklog.repository.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "library")
public class GameEntry {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer mId;

    @ColumnInfo(name = "cover_url")
    private String mCoverUrl;

    @ColumnInfo(name = "first_release_date")
    private Long mFirstReleaseDate;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "summary")
    private String mSummary;

    public GameEntry(@NonNull Integer id, String coverUrl, Long firstReleaseDate, String name,
                     String summary) {
        mId = id;
        mCoverUrl = coverUrl;
        mFirstReleaseDate = firstReleaseDate;
        mName = name;
        mSummary = summary;
    }

    @NonNull
    public Integer getId() {
        return mId;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public Long getFirstReleaseDate() {
        return mFirstReleaseDate;
    }

    public String getName() {
        return mName;
    }

    public String getSummary() {
        return mSummary;
    }
}
