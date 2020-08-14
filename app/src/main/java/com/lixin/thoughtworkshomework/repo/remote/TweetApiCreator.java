package com.lixin.thoughtworkshomework.repo.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TweetApiCreator {
    private Retrofit retrofit;

    private volatile static TweetApiCreator instance;

    public static TweetApiCreator getInstance() {
        if (instance == null) {
            synchronized (TweetApiCreator.class) {
                if (instance == null) {
                    instance = new TweetApiCreator();
                }
            }
        }

        return instance;
    }

    private TweetApiCreator() {
        Interceptor interceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder()
                        .addHeader("Accept", "application/json;responseformat=1")
                        .addHeader("Accept-Language", "zh-CN")
                        .addHeader("Connection", "close")
                        .addHeader("Content-Type", "application/json");
//                try {
//                    String accessToken = getToken();
//                    if (!TextUtils.isEmpty(accessToken)) {
//                        builder.addHeader("AUTHORIZATION", accessToken);
//                    }
//                } catch (Throwable throwable) {
//                    //
//                }
                return chain.proceed(builder.build());
            }
        };

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String message) {

                Log.d("HttpLoggingInterceptor", message);
            }
        });
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(TweetsApi.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()))
                .build();
    }

//    private String getToken() {
//        //todo
//        return null;
//    }

    public TweetsApi create() {
        return create(TweetsApi.class);
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
