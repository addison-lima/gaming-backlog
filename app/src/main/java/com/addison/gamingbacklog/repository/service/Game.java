package com.addison.gamingbacklog.repository.service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Game {
    @SerializedName("id")
    private Integer mId;
    @SerializedName("cover")
    private Cover mCover;
    @SerializedName("first_release_date")
    private Long mFirstReleaseDate;
    @SerializedName("name")
    private String mName;
    @SerializedName("summary")
    private String mSummary;
    @SerializedName("videos")
    private List<Video> mVideos;

    public Integer getId() {
        return mId;
    }

    public Cover getCover() {
        return mCover;
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

    public List<Video> getVideos() {
        return mVideos;
    }
}
