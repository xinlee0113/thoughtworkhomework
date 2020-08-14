package com.lixin.thoughtworkshomework.repo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.lixin.thoughtworkshomework.model.ProfileModel;

/**
 * @author lixin
 * @date 2020/8/14.
 */
@Entity(tableName = "profile")
public class ProfileEntity implements ProfileModel {@PrimaryKey(autoGenerate = true)
public int id;
    @ColumnInfo(name = "profileImg")
    public String profileImg;
    @ColumnInfo(name = "avatar")
    public String avatar;
    @ColumnInfo(name = "nick")
    public String nick;
    @ColumnInfo(name = "userName")
    public String userName;

    @Override
    public String getProfileImg() {
        return null;
    }

    @Override
    public String getAtatar() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

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
}
