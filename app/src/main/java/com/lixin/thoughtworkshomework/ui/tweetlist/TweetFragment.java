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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lixin.thoughtworkshomework.R;
import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public class TweetFragment extends Fragment {
    public static String TAG = "TweetFragment";
    private String userName = "jsmith";
    private static TweetFragment sInstance;
    private TweetViewModel tweetViewModel;

    @BindView(R.id.img_avatar)
    ImageView imgAvatar;

    @BindView(R.id.img_profile)
    ImageView imgProfile;

    @BindView(R.id.tv_nick)
    TextView tvNick;

    //智能刷新布局
    @BindView(R.id.srl_tweet_list)
    SmartRefreshLayout srlTweetList;

    @BindView(R.id.ry_tweet_list)
    RecyclerView ryTweetList;

    private Unbinder butterKnifeBinder;

    public static TweetFragment newInstance() {
        if (sInstance == null) {
            synchronized (TweetFragment.class) {
                sInstance = new TweetFragment();
            }
        }
        return sInstance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_tweets, container, false);
        butterKnifeBinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        tweetViewModel = new ViewModelProvider(requireActivity()).get(TweetViewModel.class);
        //绑定界面
        bindProfileDataToView();
        bindTweetDataToView();
    }

    private void bindTweetDataToView() {
        //TODO
        //1. 创建TweetListAdapter   PagedListAdapter
        //2. TweetListViewModel
        //2.1 config\ factory\ datasource
        //3. 数据加载策略
        //分段加载数据，首先从本地加载数据、加载完后。
        // 从网络加载，刷新本地数据，刷新ui， 对于ViewModel， 唯一数据来源仍是数据库
        // 主动请求网络数据后，需要主动刷新本地数据。
        TweetListAdapter adapter = new TweetListAdapter();
        ryTweetList.setAdapter(adapter);
        ryTweetList.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void bindProfileDataToView() {
        Log.i(TAG, "listenToDataChanged");
        tweetViewModel.getObservableProfile(userName).observe(getViewLifecycleOwner(), new Observer<ProfileEntity>() {
            @Override
            public void onChanged(ProfileEntity profileEntity) {
                Log.i(TAG, "listenToDataChanged onChanged profileEntity=" + profileEntity);
                Log.e(TAG, "profile img can not be used, =" + profileEntity.profileImg);
                tvNick.setText(profileEntity.nick);
                Glide.with(TweetFragment.this).load(profileEntity.profileImg).into(imgProfile);
                Glide.with(TweetFragment.this).load(profileEntity.avatar).into(imgAvatar);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        butterKnifeBinder.unbind();
    }
}
