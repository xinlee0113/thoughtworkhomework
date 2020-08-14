package com.lixin.thoughtworkshomework.model;

import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public interface TweetModel {
    String getUserName();
    String getContent();
    List<TweetEntity.Image> getImages();
    TweetEntity.Sender getSender();
    List<TweetEntity.Comment> getComments();
    String getErrorMsg();


}
