package com.addison.gamingbacklog.ui.details;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addison.gamingbacklog.repository.service.models.Video;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosAdapterViewHolder> {

    private final VideosAdapterOnClickHandler mOnClickHandler;
    private List<Video> mData;

    public interface VideosAdapterOnClickHandler {
        void onClick(Video video);
    }

    public VideosAdapter(VideosAdapterOnClickHandler onClickHandler) {
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public VideosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VideosAdapterViewHolder holder, int position) {
        Video video = mData.get(position);
        Log.d("ADD_TEST", "video name: " + video.getName());
        Log.d("ADD_TEST", "video thumbnail url: " + video.getThumbnailUrl());
        Log.d("ADD_TEST", "video app intent: " + video.getAppIntent());
        Log.d("ADD_TEST", "video web intent: " + video.getWebIntent());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Video> data) {
        mData = data;
    }

    public class VideosAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public VideosAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickHandler != null) {
                int adapterPosition = getAdapterPosition();
                mOnClickHandler.onClick(mData.get(adapterPosition));
            }
        }
    }
}
