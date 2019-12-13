package com.addison.gamingbacklog.ui.details;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addison.gamingbacklog.databinding.VideoItemBinding;
import com.addison.gamingbacklog.repository.service.models.Video;
import com.bumptech.glide.Glide;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosAdapterViewHolder> {

    private final VideosAdapterOnClickHandler mOnClickHandler;
    private Context mContext;
    private List<Video> mData;

    public interface VideosAdapterOnClickHandler {
        void onClick(Video video);
    }

    public VideosAdapter(Context context, VideosAdapterOnClickHandler onClickHandler) {
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public VideosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        VideoItemBinding videoItemBinding = VideoItemBinding.inflate(layoutInflater, parent, false);
        return new VideosAdapter.VideosAdapterViewHolder(videoItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosAdapterViewHolder holder, int position) {
        if (mData != null) {
            holder.bind(mData.get(position));
        }
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
        notifyDataSetChanged();
    }

    public class VideosAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final VideoItemBinding mBinding;

        public VideosAdapterViewHolder(VideoItemBinding videoItemBinding) {
            super(videoItemBinding.getRoot());
            mBinding = videoItemBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(Video video) {
            if (video != null) {
                Glide.with(mContext.getApplicationContext())
                        .load(video.getThumbnailUrl())
                        .centerCrop()
                        .into(mBinding.ivVideoThumbnail);
                mBinding.tvVideoName.setText(video.getName());
                mBinding.executePendingBindings();
            }
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
