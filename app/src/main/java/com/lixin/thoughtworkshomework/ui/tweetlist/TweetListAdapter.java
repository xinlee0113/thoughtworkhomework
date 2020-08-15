package com.lixin.thoughtworkshomework.ui.tweetlist;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public class TweetListAdapter extends PagedListAdapter<TweetEntity, TweetListAdapter.TweetViewHolder> {

    public static final DiffUtil.ItemCallback<TweetEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<TweetEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TweetEntity oldItem, @NonNull TweetEntity newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TweetEntity oldItem, @NonNull TweetEntity newItem) {
            return oldItem.id == newItem.id;
        }
    };
    protected TweetListAdapter(){
        super(DIFF_CALLBACK);
    }
    protected TweetListAdapter(@NonNull AsyncDifferConfig<TweetEntity> config) {
        super(config);
    }

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TweetViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {
        TweetEntity entity = getItem(position);
        if (entity != null) {
            holder.bindTo(getItem(position));
        } else {
            holder.clear();
        }
    }


    public static class TweetViewHolder extends RecyclerView.ViewHolder {

        public TweetViewHolder(View itemView) {
            super(itemView);

        }

        public void bindTo(TweetEntity item) {
            //TODO
        }

        public void clear() {

        }
    }


}
