package com.addison.gamingbacklog.ui.discover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addison.gamingbacklog.databinding.GameItemBinding;
import com.addison.gamingbacklog.repository.service.models.Game;

import java.util.Calendar;
import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverAdapterViewHolder> {

    private final DiscoverAdapterOnClickHandler mOnClickHandler;
    private List<Game> mData;

    public interface DiscoverAdapterOnClickHandler {
        void onClick(Game game);
    }

    public DiscoverAdapter(DiscoverAdapterOnClickHandler onClickHandler) {
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public DiscoverAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        GameItemBinding gameItemBinding = GameItemBinding.inflate(layoutInflater, parent, false);
        return new DiscoverAdapterViewHolder(gameItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverAdapterViewHolder holder, int position) {
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

    public void setData(List<Game> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public class DiscoverAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final GameItemBinding mBinding;

        public DiscoverAdapterViewHolder(GameItemBinding gameItemBinding) {
            super(gameItemBinding.getRoot());
            mBinding = gameItemBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(Game game) {
            if (game != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(game.getFirstReleaseDate() * 1000);
                mBinding.tvGameName.setText(game.getName());
                mBinding.tvGameRelease.setText(String.valueOf(calendar.get(Calendar.YEAR)));
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
