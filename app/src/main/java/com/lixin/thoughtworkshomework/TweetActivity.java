package com.lixin.thoughtworkshomework;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.lixin.thoughtworkshomework.ui.tweetlist.TweetFragment;

/**
 * @author lixin
 * 朋友圈Activity
 */
public class TweetActivity extends AppCompatActivity {

    private TweetFragment mTweetFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        if (savedInstanceState == null) {
            showTweetFragment();
        }
    }

    /**
     * 显示朋友圈
     */
    void showTweetFragment() {
        if (mTweetFragment == null) {
            mTweetFragment = new TweetFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mTweetFragment, TweetFragment.TAG).commit();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).onLowMemory();
    }


}