package com.lixin.thoughtworkshomework.module.moments.repo.remote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.espresso.base.MainThread;

import com.lixin.thoughtworkshomework.module.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.module.repo.remote.RemoteDataSource;

import org.junit.Test;
import org.mockito.Mock;

/**
 * @author lixin
 * @date 2020/8/18.
 */
public class RemoteDataSourceTest {

    private static final String TAG = "RemoteDataSourceTest";
    @Mock
    RemoteDataSource remoteDataSource = new RemoteDataSource();
    private final String USER_NAME = "jsmith";


    @MainThread
    @Test
    public void testGetProfile() {
        LiveData<ProfileEntity> profile = remoteDataSource.getProfile(USER_NAME);
        profile.observe(null, new Observer<ProfileEntity>() {
            @Override
            public void onChanged(ProfileEntity profileEntity) {
                Log.i(TAG,"profile="+profileEntity.toString());
            }
        });

    }

    @Test
    public void testGetTweets() {

    }
}
