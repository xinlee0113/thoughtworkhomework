package com.lixin.thoughtworkshomework.module.moments.ui;

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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lixin.library.base.aroute.Constants;
import com.lixin.library.base.aroute.RouterPath;
import com.lixin.module.moments.R;
import com.lixin.module.moments.R2;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lixin
 * @date 2020/8/14.
 * 朋友圈界面
 */

@Route(path = RouterPath.Fragment.TWEET)
public class MomentsFragment extends Fragment {
    public static final String TAG = "TweetFragment";

    @Autowired(name = Constants.EntryParams.KEY)
    int entryCode = Constants.EntryParams.Code.DEFAULT;

    @Nullable
    @BindView(R2.id.img_avatar)
    ImageView mImgAvatar;
    @Nullable
    @BindView(R2.id.img_profile)
    ImageView mImgProfile;
    @Nullable
    @BindView(R2.id.tv_nick)
    TextView mTvNick;
    @Nullable
    @BindView(R2.id.srl_tweet_list)
    SmartRefreshLayout mSrlTweetList;
    @Nullable
    @BindView(R2.id.ry_tweet_list)
    RecyclerView mRyTweetList;
    private MomentsViewModel mMomentsViewModel;
    private Unbinder mButterKnifeBinder;

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
        mMomentsViewModel = new ViewModelProvider(requireActivity()).get(MomentsViewModel.class);
        //绑定界面
        bindProfileDataToView();
        bindTweetDataToView();
    }

    @VisibleForTesting
    public void bindTweetDataToView() {
        //TODO
        //1. 创建TweetListAdapter   PagedListAdapter
        //2. TweetListViewModel
        //2.1 config\ factory\ datasource
        //3. 数据加载策略

        TweetListAdapter adapter = new TweetListAdapter();
        Objects.requireNonNull(mRyTweetList).setAdapter(adapter);
        mRyTweetList.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRyTweetList.setNestedScrollingEnabled(true);
        mMomentsViewModel.getObservableTweetList("jsmith", false).observe(requireActivity(), pagedList -> {
            adapter.submitList(pagedList);
        });
    }

    private void bindProfileDataToView() {
        Log.i(TAG, "listenToDataChanged");
        String userName = "jsmith";
        mMomentsViewModel.getObservableProfile(userName).observe(getViewLifecycleOwner(), profileEntity -> {
            Log.i(TAG, "listenToDataChanged onChanged profileEntity=" + profileEntity);
            Log.e(TAG, "profile img can not be used, =" + profileEntity.profileImg);
            Objects.requireNonNull(mTvNick).setText(profileEntity.nick);
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 100);

            Glide.with(MomentsFragment.this).load(profileEntity.profileImg).error(R.drawable.icon_head_bg).into(Objects.requireNonNull(mImgProfile));
            Glide.with(MomentsFragment.this).load(profileEntity.avatar).apply(options).error(R.drawable.icon_head).into(Objects.requireNonNull(mImgAvatar));
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mButterKnifeBinder.unbind();
    }
}
