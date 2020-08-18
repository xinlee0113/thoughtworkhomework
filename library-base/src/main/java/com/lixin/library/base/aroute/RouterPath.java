package com.lixin.library.base.aroute;

public interface RouterPath {

    String BASE_URI_STRING = "arouter://com.lixin.thoughtworkshomework%s";

    interface Activity {
        String ENTRY = "/stub/EntryActivity";

        String MOMENTS = "/moments/TweetActivity";
    }

    interface Fragment {
        // tweet
        String TWEET = "/moments/TweetFragment";

    }
}