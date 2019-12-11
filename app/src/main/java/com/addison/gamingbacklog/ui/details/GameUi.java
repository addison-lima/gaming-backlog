package com.addison.gamingbacklog.ui.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.addison.gamingbacklog.repository.service.models.Game;

public class GameUi implements Parcelable {

    private Integer mId;
    private String mCoverUrl;
    private Long mFirstReleaseDate;
    private String mName;
    private String mSummary;

    public GameUi(Integer id, String coverUrl, Long firstReleaseDate, String name, String summary) {
        mId = id;
        mCoverUrl = coverUrl;
        mFirstReleaseDate = firstReleaseDate;
        mName = name;
        mSummary = summary;
    }

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

    protected GameUi(Parcel in) {
        mId = in.readInt();
        mCoverUrl = in.readString();
        mFirstReleaseDate = in.readLong();
        mName = in.readString();
        mSummary = in.readString();
    }

    public static final Creator<GameUi> CREATOR = new Creator<GameUi>() {
        @Override
        public GameUi createFromParcel(Parcel in) {
            return new GameUi(in);
        }

        @Override
        public GameUi[] newArray(int size) {
            return new GameUi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mCoverUrl);
        parcel.writeLong(mFirstReleaseDate);
        parcel.writeString(mName);
        parcel.writeString(mSummary);
    }
}
