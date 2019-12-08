package com.addison.gamingbacklog.ui.discover;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverAdapterViewHolder holder, int position) {
        Game game = mData.get(position);
        Log.d("ADD_TEST", "id: " + game.getId());
        Log.d("ADD_TEST", "name: " + game.getName());
        Log.d("ADD_TEST", "summary: " + game.getSummary());
        Log.d("ADD_TEST", "cover: " + game.getCover().getUrl());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(game.getFirstReleaseDate() * 1000);
        Log.d("ADD_TEST", "first_release_date: " + calendar.get(Calendar.YEAR));
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
    }

    public class DiscoverAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public DiscoverAdapterViewHolder(@NonNull View itemView) {
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
