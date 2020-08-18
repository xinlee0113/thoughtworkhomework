package com.lixin.thoughtworkshomework.module.moments.repo.entity;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lixin.thoughtworkshomework.module.moments.repo.local.converter.CommentConverter;
import com.lixin.thoughtworkshomework.module.moments.repo.local.converter.ImageConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @author lixin
 * @date 2020/8/14.
 * 朋友圈信息实体类
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TweetEntity)) {
            return false;
        }
        TweetEntity entity = (TweetEntity) o;
        return
                userName.equals(entity.userName) &&
                        content.equals(entity.content) &&
                        images == entity.images &&
                        sender == entity.sender &&
                        comments == entity.comments &&
                        error.equals(entity.error) &&
                        unknown_error.equals(entity.unknown_error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, content, images, sender, comments, error, unknown_error);
    }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Comment)) {
                return false;
            }
            Comment comment = (Comment) o;
            return content.equals(comment.content) &&
                    sender.equals(comment.sender);
        }

        @Override
        public int hashCode() {
            return Objects.hash(content, sender);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Image)) {
                return false;
            }
            Image image = (Image) o;
            return url.equals(image.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(url);
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
