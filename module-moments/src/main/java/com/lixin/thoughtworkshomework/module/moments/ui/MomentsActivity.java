package com.lixin.thoughtworkshomework.module.moments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.lixin.library.base.BaseActivity;
import com.lixin.library.base.aroute.Constants;
import com.lixin.library.base.aroute.RouterPath;
import com.lixin.module.moments.R;

/**
 * @author lixin
 * 朋友圈Activity
 */
@Route(path = RouterPath.Activity.MOMENTS)
public class MomentsActivity extends BaseActivity {

    @Autowired(name = Constants.EntryParams.KEY)
    int entryCode = Constants.EntryParams.Code.DEFAULT;

    private MomentsFragment mMomentsFragment;

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
    @VisibleForTesting
    public void showTweetFragment() {
        if (mMomentsFragment == null) {
            mMomentsFragment = new MomentsFragment();
        }
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, mTweetFragment, TweetFragment.TAG).commit();


        Fragment tweetFragment = (MomentsFragment) ARouter.getInstance()
                .build(RouterPath.Fragment.TWEET)
                .with(getIntent().getExtras())
                .navigation();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, tweetFragment)
                .commitAllowingStateLoss();
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