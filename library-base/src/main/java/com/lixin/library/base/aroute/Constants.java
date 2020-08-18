package com.lixin.library.base.aroute;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Constants {
    interface CustomAction {
        /**
         * 打卡
         */
        String PUNCH_IN_ACTION = "receiver.dailypunchin.action";
    }

    interface EntryParams {
        /**
         * Key
         */
        String KEY = "EntryCode";

        /**
         * Value
         */
        interface Code {
            /**
             * 默认
             */
            int DEFAULT = 0;
            /**
             * 朋友圈
             */
            int MOMENTS =1;
        }
    }

    /**
     * 扫码登录状态码
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LoginStatus.ARRIVED, LoginStatus.SUCCESS, LoginStatus.FAILURE})
    @interface LoginStatus {
        /**
         * 扫完码
         */
        int ARRIVED = 0;
        /**
         * 扫码登录成功
         */
        int SUCCESS = 1;
        /**
         * 扫码登录失败
         */
        int FAILURE = 2;
    }

    String GENDER_MALE = "男";
    String GENDER_FEMALE = "女";
}