package com.lixin.thoughtworkshomework.module.repo.local.converter;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lixin.thoughtworkshomework.module.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 * 评论信息、Json 互转
 */
public class CommentConverter {


    @TypeConverter
    public String convertCommentListToString(List<TweetEntity.Comment> list) {
        return new Gson().toJson(list);
    }

    @Nullable
    @TypeConverter
    public List<TweetEntity.Comment> convertStringToComment(String json) {
        try {
            return new Gson().fromJson(json, new TypeToken<List<TweetEntity.Comment>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
