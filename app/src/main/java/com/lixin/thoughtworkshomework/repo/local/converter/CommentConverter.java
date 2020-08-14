package com.lixin.thoughtworkshomework.repo.local.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public class CommentConverter {


    @TypeConverter
    public String convertCommentListToString(List<TweetEntity.Comment> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public List<TweetEntity.Comment> convertStringToComment(String json) {
        try {
            return new Gson().fromJson(json, new TypeToken<List<TweetEntity.Comment>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
