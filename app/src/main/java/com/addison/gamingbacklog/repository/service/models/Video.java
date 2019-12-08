package com.addison.gamingbacklog.repository.service.models;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("id")
    private Integer mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("video_id")
    private String mVideoId;

    public Integer getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getThumbnailUrl() {
        return "https://img.youtube.com/vi/" + mVideoId + "/hqdefault.jpg";
    }

    public String getAppIntent() {
        return "vnd.youtube:" + mVideoId;
    }

    public String getWebIntent() {
        return "http://www.youtube.com/watch?v=" + mVideoId;
    }
}
