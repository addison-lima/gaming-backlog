package com.addison.gamingbacklog.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addison.gamingbacklog.databinding.GameItemBinding;
import com.addison.gamingbacklog.repository.database.GameEntry;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameAdapterViewHolder> {
    private final GameAdapterOnClickHandler mOnClickHandler;
    private Context mContext;
    private List<GameEntry> mData;

    public interface GameAdapterOnClickHandler {
        void onClick(GameEntry gameEntry);
    }

    public GameAdapter(Context context, GameAdapterOnClickHandler onClickHandler) {
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public GameAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        GameItemBinding gameItemBinding = GameItemBinding.inflate(layoutInflater, parent, false);
        return new GameAdapterViewHolder(gameItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapterViewHolder holder, int position) {
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

    public void setData(List<GameEntry> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public class GameAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final GameItemBinding mBinding;

        public GameAdapterViewHolder(GameItemBinding gameItemBinding) {
            super(gameItemBinding.getRoot());
            mBinding = gameItemBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(GameEntry game) {
            if (game != null) {
                if (game.getCoverUrl() != null) {
                    Glide.with(mContext.getApplicationContext())
                            .load(game.getCoverUrl())
                            .centerCrop()
                            .into(mBinding.ivGameCover);
                }
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
