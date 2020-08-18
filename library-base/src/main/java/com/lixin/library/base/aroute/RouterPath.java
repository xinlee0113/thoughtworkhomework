package com.lixin.library.base.aroute;

/**
 * @author lixin
 */
public interface RouterPath {

    String BASE_URI_STRING = "arouter://com.lixin.thoughtworkshomework%s";

    interface Activity {
        String ENTRY = "/stub/EntryActivity";

        String MOMENTS = "/module/moments/TweetActivity";
    }

    interface Fragment {
        // tweet
        String TWEET = "/module/moments/TweetFragment";

    }
}