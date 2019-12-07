package com.addison.gamingbacklog.repository.service;

import com.google.gson.annotations.SerializedName;

public class Game {
    @SerializedName("id")
    private Integer mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("summary")
    private String mSummary;

    public Integer getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getSummary() {
        return mSummary;
    }
}
