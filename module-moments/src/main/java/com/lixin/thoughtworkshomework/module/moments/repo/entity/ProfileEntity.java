package com.lixin.thoughtworkshomework.module.moments.repo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author lixin
 * @date 2020/8/14.
 * 用户信息实体类
 */
@Entity(tableName = "profile")
public class ProfileEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "profileImg")
    public String profileImg;
    @ColumnInfo(name = "avatar")
    public String avatar;
    @ColumnInfo(name = "nick")
    public String nick;
    @ColumnInfo(name = "userName")
    public String userName;

    @NotNull
    @Override
    public String toString() {
        return "ProfileEntity{" +
                "id=" + id +
                ", profileImg='" + profileImg + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileEntity that = (ProfileEntity) o;
        return
                profileImg.equals(that.profileImg) &&
                        avatar.equals(that.avatar) &&
                        nick.equals(that.nick) &&
                        userName.equals(that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileImg, avatar, nick, userName);
    }
}
