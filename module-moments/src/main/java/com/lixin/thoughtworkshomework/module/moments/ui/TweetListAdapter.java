package com.lixin.thoughtworkshomework.module.moments.ui;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lixin.module.moments.R;
import com.lixin.thoughtworkshomework.module.moments.repo.entity.TweetEntity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author lixin
 * @date 2020/8/14.
 * 朋友圈列表适配器，支持分页加载，使用JetPack的PagingList组件
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
        if (entity != null && entity.error == null && entity.unknown_error == null) {
            holder.bindTo(Objects.requireNonNull(getItem(position)));
        } else {
            holder.clear();
        }
    }


    public static class TweetViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TweetViewHolder";
        final TextView tvContent;
        //        TextView tvTweetSenderName;
        final TextView tvTweetSenderNick;
        final ImageView imgTweetSenderAvatar;
        final RecyclerView ryComments;
        final RecyclerView ryImages;


        BaseQuickAdapter<TweetEntity.Image, BaseViewHolder> tweetImgAdapter;
        BaseQuickAdapter<TweetEntity.Comment, BaseViewHolder> tweetCommentAdapter;

        public TweetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_tweet_content);
            ryImages = itemView.findViewById(R.id.ry_tweet_images);
            ryComments = itemView.findViewById(R.id.ry_tweet_comments);
            tvTweetSenderNick = itemView.findViewById(R.id.tv_tweet_sender_nick);
            imgTweetSenderAvatar = itemView.findViewById(R.id.tv_tweet_sender_avatar);
        }

        public void bindTo(@NonNull TweetEntity item) {
            //TODO
            tvContent.setText(item.content);
            tvTweetSenderNick.setText(item.sender != null ? item.sender.nick : "");
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(5);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(50, 50);

            Glide.with(imgTweetSenderAvatar)
                    .load(item.sender != null ? item.sender.avatar : null)
                    .error(R.drawable.icon_head)
                    .placeholder(R.drawable.icon_head)
                    .apply(options)
                    .into(imgTweetSenderAvatar);

            bindTweetImage(item);

            bindTweetComment(item);

        }

        private void bindTweetComment(@NonNull TweetEntity item) {
            ryComments.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            if (tweetCommentAdapter == null) {
                tweetCommentAdapter = new BaseQuickAdapter<TweetEntity.Comment, BaseViewHolder>(R.layout.item_tweet_comment, item.comments) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NonNull TweetEntity.Comment comment) {
                        if (comment.sender != null) {
                            SpannableString content = new SpannableString(comment.sender.nick + " : " + comment.content);
                            content.setSpan(new StyleSpan(Typeface.BOLD), 0, comment.sender.nick.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            Log.i(TAG, "comment=" + content);
                            baseViewHolder.setText(R.id.tv_tweet_comment_content, content);
                        }

                    }
                };
                ryComments.setAdapter(tweetCommentAdapter);
            } else {
                tweetCommentAdapter.notifyDataSetChanged();
            }
        }

        private void bindTweetImage(@NonNull TweetEntity item) {
            ryImages.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
            ryImages.addItemDecoration(new TweetImageDecoration(3, 10, false));
            if (tweetImgAdapter == null) {
                tweetImgAdapter = new BaseQuickAdapter<TweetEntity.Image, BaseViewHolder>(R.layout.item_tweet_img, item.images) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NonNull TweetEntity.Image img) {
                        ImageView imgItemView = baseViewHolder.findView(R.id.img_tweet);
                        RoundedCorners roundedCorners = new RoundedCorners(5);
                        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(100, 100);
                        Glide.with(Objects.requireNonNull(imgItemView))
                                .load(img.url)
                                .apply(options)
                                .error(R.drawable.img1)
                                .into(imgItemView);
                    }
                };
                ryImages.setAdapter(tweetImgAdapter);
            } else {
                tweetImgAdapter.notifyDataSetChanged();
            }
        }

        public void clear() {
            //TODO
            tweetCommentAdapter = null;
            tweetImgAdapter = null;
        }
    }


}
