package com.lixin.thoughtworkshomework.ui;

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

import com.bumptech.glide.Glide;
import com.lixin.thoughtworkshomework.R;
import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;
import com.lixin.thoughtworkshomework.viewmodel.TweetViewModel;

import java.util.List;

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
    private TweetViewModel viewModel;

    @BindView(R.id.img_avatar)
    ImageView imgAvatar;

    @BindView(R.id.img_profile)
    ImageView imgProfile;

    @BindView(R.id.tv_nick)
    TextView tvNick;
    private Unbinder unbinder;

    public static TweetFragment newInstance() {
        if (sInstance == null) {
            synchronized (TweetFragment.class) {
                sInstance = new TweetFragment();
            }
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_tweets, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        TweetViewModel.Factory factory = new TweetViewModel.Factory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(), factory).get(TweetViewModel.class);
        listenToDataChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void listenToDataChanged() {
        Log.i(TAG, "listenToDataChanged");
        viewModel.getObservableProfile(userName).observe(getViewLifecycleOwner(), new Observer<ProfileEntity>() {
            @Override
            public void onChanged(ProfileEntity profileEntity) {
                Log.i(TAG, "listenToDataChanged onChanged profileEntity=" + profileEntity);
                refreshProfileView(profileEntity);
            }
        });
        viewModel.getObservableTweet(userName).observe(getViewLifecycleOwner(), new Observer<List<TweetEntity>>() {
            @Override
            public void onChanged(List<TweetEntity> tweetEntities) {
                Log.i(TAG, "listenToDataChanged onChanged tweetEntities=" + tweetEntities);
                refreshTweetListView(tweetEntities);
            }
        });
    }

    private void refreshTweetListView(List<TweetEntity> tweetEntities) {


    }

    private void refreshProfileView(ProfileEntity profileEntity) {
        Log.e(TAG, "profile img can not be used, =" + profileEntity.profileImg);
        tvNick.setText(profileEntity.nick);
        Glide.with(this).load(profileEntity.profileImg).error(R.drawable.ic_launcher_background).into(imgProfile);
        Glide.with(this).load(profileEntity.avatar).into(imgAvatar);
    }
}
