package com.lixin.thoughtworkshomework.repo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

/**
 * @author lixin
 * @date 2020/8/14.
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
}
