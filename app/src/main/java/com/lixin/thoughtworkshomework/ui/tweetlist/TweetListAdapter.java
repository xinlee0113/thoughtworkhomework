package com.lixin.thoughtworkshomework.ui.tweetlist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lixin.thoughtworkshomework.R;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    protected TweetListAdapter() {
        super(DIFF_CALLBACK);
    }

    protected TweetListAdapter(@NonNull AsyncDifferConfig<TweetEntity> config) {
        super(config);
    }

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_tweet, null);
        return new TweetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {
        TweetEntity entity = getItem(position);
        if (entity != null) {
            holder.bindTo(Objects.requireNonNull(getItem(position)));
        } else {
            holder.clear();
        }
    }


    public static class TweetViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvId;
        TextView tvUserName;
        TextView tvError;
        TextView tvTweetSenderName;
        TextView tvTweetSenderNick;
        ImageView imgTweetSenderAvatar;
        RecyclerView ryComments;
        RecyclerView ryImages;


        BaseQuickAdapter<TweetEntity.Image, BaseViewHolder> tweetImgAdapter;
        BaseQuickAdapter<TweetEntity.Comment, BaseViewHolder> tweetCommentAdapter;

        public TweetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_tweet_content);
//            tvId = itemView.findViewById(R.id.tv_id);
//            tvUserName = itemView.findViewById(R.id.tv_user_name);
//            tvError = itemView.findViewById(R.id.tv_error);
//            ryImages = itemView.findViewById(R.id.ry_twee_images);
//            ryComments = itemView.findViewById(R.id.ry_tweet_comments);
//
//            tvTweetSenderName = itemView.findViewById(R.id.tv_tweet_sender_user_name);
//            tvTweetSenderNick = itemView.findViewById(R.id.tv_tweet_sender_nick);
//            imgTweetSenderAvatar = itemView.findViewById(R.id.tv_tweet_sender_avatar);
        }

        public void bindTo(@NonNull TweetEntity item) {
            //TODO
            tvContent.setText(item.content);
//            tvId.setText(item.id+"");
//            tvUserName.setText(item.userName);
//            tvError.setText(item.error != null ? item.error : item.unknown_error);
//
//            tvTweetSenderName.setText(item.sender.username);
//            tvTweetSenderNick.setText(item.sender.nick);
//            Glide.with(imgTweetSenderAvatar).load(item.sender.avatar).into(imgTweetSenderAvatar);
//
//            bindTweetImage(item);
//
//            bindTweetComment(item);

        }

        private void bindTweetComment(@NonNull TweetEntity item) {
            ryComments.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            if (tweetCommentAdapter == null) {
                tweetCommentAdapter = new BaseQuickAdapter<TweetEntity.Comment, BaseViewHolder>(R.layout.item_tweet_comment, item.comments) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NonNull TweetEntity.Comment comment) {
                        baseViewHolder.setText(R.id.tv_tweet_comment_content, comment.content);
                        baseViewHolder.setText(R.id.tv_tweet_comment_sender_user_name, comment.sender.username);
                        baseViewHolder.setText(R.id.tv_tweet_comment_sender_nick, comment.sender.nick);
                        ImageView sendAvatarImgView = baseViewHolder.findView(R.id.tv_tweet_comment_sender_avatar);
                        Glide.with(Objects.requireNonNull(sendAvatarImgView)).load(comment.sender.avatar).into(sendAvatarImgView);
                    }
                };
                ryComments.setAdapter(tweetCommentAdapter);
            } else {
                tweetCommentAdapter.notifyDataSetChanged();
            }
        }

        private void bindTweetImage(@NonNull TweetEntity item) {
            ryImages.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
            if (tweetImgAdapter == null) {
                tweetImgAdapter = new BaseQuickAdapter<TweetEntity.Image, BaseViewHolder>(R.layout.item_tweet_img, item.images) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NonNull TweetEntity.Image img) {
                        ImageView imgItemView = baseViewHolder.findView(R.id.img_tweet);
                        Glide.with(Objects.requireNonNull(imgItemView)).load(img.url).error(R.drawable.img_t).into(imgItemView);
                    }
                };
                ryImages.setAdapter(tweetImgAdapter);
            } else {
                tweetImgAdapter.notifyDataSetChanged();
            }
        }

        public void clear() {
            //TODO
        }
    }


}
