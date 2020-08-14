package com.lixin.thoughtworkshomework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lixin.thoughtworkshomework.ui.TweetFragment;

/**
 * @author lixin
 */
public class TweetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        if (savedInstanceState==null){
            showTweetFragment();
        }
    }

    /**
     * 显示朋友圈
     */
    void showTweetFragment(){
        TweetFragment fragment=TweetFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, TweetFragment.TAG).commit();
    }
}