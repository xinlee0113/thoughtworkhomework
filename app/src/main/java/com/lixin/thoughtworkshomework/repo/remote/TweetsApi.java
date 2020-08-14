package com.lixin.thoughtworkshomework.repo.remote;

import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author lixin
 * @date 2020/8/13.
 * http://thoughtworks-ios.herokuapp.com/user/jsmith
 * http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets
 */
public interface TweetsApi {
    public static final String BASE_URL = "http://thoughtworks-ios.herokuapp.com";

    /**
     * @param userName 用户名
     * @return 用户信息
     * 获取用户信息
     */
    @GET("/user/{userName}")
    Observable<ProfileEntity> getProfile(@Path("userName") String userName);


    /**
     * @param userName 用户名
     * @return 朋友圈列表
     * 获取朋友圈列表信息
     */
    @GET("/user/{userName}/tweets")
    Observable<List<TweetEntity>> getTweets(@Path("userName") String userName);
}

