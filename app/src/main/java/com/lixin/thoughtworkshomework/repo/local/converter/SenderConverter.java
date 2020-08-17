package com.lixin.thoughtworkshomework.repo.local.converter;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import java.io.IOException;
import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 * 发送者对象、Json互转
 */
public class SenderConverter {
    @TypeConverter
    public String convertSenderToString(TweetEntity.Sender sender) {
        return new Gson().toJson(sender, TweetEntity.Sender.class);
    }

    @TypeConverter
    public String convertSenderListToString(List<TweetEntity.Sender> list) {
        return new Gson().toJson(list);
    }

    @Nullable
    @TypeConverter
    public TweetEntity.Sender convertStringToSender(String json) {
        try {
            return new Gson().getAdapter(TweetEntity.Sender.class).fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
