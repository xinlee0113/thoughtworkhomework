package com.lixin.thoughtworkshomework.module.moments.repo.local.converter;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lixin.thoughtworkshomework.module.moments.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 * 图片列表、Json 互转
 */

public class ImageConverter {
    @TypeConverter
    public String convertImageListToString(List<TweetEntity.Image> list) {
        return new Gson().toJson(list);
    }

    @Nullable
    @TypeConverter
    public List<TweetEntity.Image> convertStringToImage(String json) {
        try {

            return new Gson().fromJson(json, new TypeToken<List<TweetEntity.Image>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
