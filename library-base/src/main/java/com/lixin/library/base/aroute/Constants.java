package com.lixin.library.base.aroute;

/**
 * @author lixin
 */
public interface Constants {
    interface CustomAction {
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
}