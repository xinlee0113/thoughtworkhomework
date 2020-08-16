package com.lixin.thoughtworkshomework.repo.entity;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lixin.thoughtworkshomework.repo.local.converter.CommentConverter;
import com.lixin.thoughtworkshomework.repo.local.converter.ImageConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 */
@Entity(tableName = "tweet")
public class TweetEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String userName;
    public String content;
    @Nullable
    @TypeConverters(ImageConverter.class)
    public List<Image> images;
    @Nullable
    public Sender sender;
    @Nullable
    @TypeConverters(CommentConverter.class)
    public List<Comment> comments;
    public String error;
    public String unknown_error;

    public static class Comment {
        public String content;
        public Sender sender;

        @NotNull
        @Override
        public String toString() {
            return "Comment{" +
                    "content='" + content + '\'' +
                    ", sender=" + sender +
                    '}';
        }
    }

    public static class Image {
        public String url;

        @NotNull
        @Override
        public String toString() {
            return "Image{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }

    public static class Sender {
        public String username;
        public String nick;
        public String avatar;

        @NotNull
        @Override
        public String toString() {
            return "Sender{" +
                    "username='" + username + '\'' +
                    ", nick='" + nick + '\'' +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }
}
