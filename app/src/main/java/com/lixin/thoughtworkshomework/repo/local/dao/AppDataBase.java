package com.lixin.thoughtworkshomework.repo.local.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lixin.thoughtworkshomework.AppExecutors;
import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;
import com.lixin.thoughtworkshomework.repo.local.converter.CommentConverter;
import com.lixin.thoughtworkshomework.repo.local.converter.ImageConverter;
import com.lixin.thoughtworkshomework.repo.local.converter.SenderConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 */
@Database(entities = {ProfileEntity.class, TweetEntity.class}, version = 1 ,exportSchema = false)
@TypeConverters({CommentConverter.class, ImageConverter.class, SenderConverter.class})
abstract public class AppDataBase extends RoomDatabase {
    public abstract ProfileDao profileDao();

    public abstract TweetDao tweetDao();

    private static AppDataBase sInstance;

    public static final String DATABASE_NAME = "demo-tweet-db";

    private final MutableLiveData<Boolean> isDbCreated = new MutableLiveData<>();



    public static AppDataBase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDataBase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context);
                }
            }
        }
        return sInstance;
    }

    private static AppDataBase buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDataBase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                // Generate the data for pre-population
                                AppDataBase database = AppDataBase.getInstance(appContext, executors);
                                List<ProfileEntity> profileEntities = new ArrayList<>();
                                List<TweetEntity> tweetEntities = new ArrayList<>();
                                //TODO insertTestData
                                database.setDatabaseCreated();
                            }
                        });
                    }
                })
                .build();
    }

    private void setDatabaseCreated() {
        isDbCreated.postValue(true);
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDbCreated;
    }


}
