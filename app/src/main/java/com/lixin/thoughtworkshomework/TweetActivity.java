package com.lixin.thoughtworkshomework;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lixin.thoughtworkshomework.ui.tweetlist.TweetFragment;

/**
 * @author lixin
 */
public class TweetActivity extends AppCompatActivity {

    private TweetFragment tweetFragment;

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
        if (tweetFragment == null) {
            tweetFragment = new TweetFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, tweetFragment, TweetFragment.TAG).commit();
    }
}