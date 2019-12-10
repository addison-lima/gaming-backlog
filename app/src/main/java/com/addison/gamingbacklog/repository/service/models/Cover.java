package com.addison.gamingbacklog.repository.service.models;

import com.google.gson.annotations.SerializedName;

public class Cover {
    @SerializedName("id")
    private Integer mId;
    @SerializedName("url")
    private String mUrl;

    public Integer getId() {
        return mId;
    }

    public String getUrl() {
        return "https:"+ mUrl;
    }
}
