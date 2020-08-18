package com.lixin.thoughtworkshomework.ui.tweetlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lixin.thoughtworkshomework.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lixin
 * @date 2020/8/14.
 * 朋友圈界面
 */
public class TweetFragment extends Fragment {
    public static final String TAG = "TweetFragment";
    private final String userName = "jsmith";
    @Nullable
    @BindView(R.id.img_avatar)
    ImageView mImgAvatar;
    @Nullable
    @BindView(R.id.img_profile)
    ImageView mImgProfile;
    @Nullable
    @BindView(R.id.tv_nick)
    TextView mTvNick;
    @Nullable
    @BindView(R.id.srl_tweet_list)
    SmartRefreshLayout mSrlTweetList;
    @Nullable
    @BindView(R.id.ry_tweet_list)
    RecyclerView mRyTweetList;
    private TweetViewModel mTweetViewModel;
    private Unbinder mButterKnifeBinder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_tweet, container, false);
        mButterKnifeBinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        mTweetViewModel = new ViewModelProvider(requireActivity()).get(TweetViewModel.class);
        //绑定界面
        bindProfileDataToView();
        bindTweetDataToView();
    }

    @VisibleForTesting
    void bindTweetDataToView() {
        //TODO
        //1. 创建TweetListAdapter   PagedListAdapter
        //2. TweetListViewModel
        //2.1 config\ factory\ datasource
        //3. 数据加载策略

        TweetListAdapter adapter = new TweetListAdapter();
        mRyTweetList.setAdapter(adapter);
        mRyTweetList.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRyTweetList.setNestedScrollingEnabled(true);
        mTweetViewModel.getObservableTweetList("jsmith", false).observe(requireActivity(), pagedList -> {
            adapter.submitList(pagedList);
        });
    }

    private void bindProfileDataToView() {
        Log.i(TAG, "listenToDataChanged");
        mTweetViewModel.getObservableProfile(userName).observe(getViewLifecycleOwner(), profileEntity -> {
            Log.i(TAG, "listenToDataChanged onChanged profileEntity=" + profileEntity);
            Log.e(TAG, "profile img can not be used, =" + profileEntity.profileImg);
            mTvNick.setText(profileEntity.nick);
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 100);

            Glide.with(TweetFragment.this).load(profileEntity.profileImg).error(R.drawable.icon_head_bg).into(mImgProfile);
            Glide.with(TweetFragment.this).load(profileEntity.avatar).apply(options).error(R.drawable.icon_head).into(mImgAvatar);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mButterKnifeBinder.unbind();
    }
}
