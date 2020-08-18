package com.lixin.thoughtworkshomework;

import android.util.Log;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author lixin
 * @date 2020/8/18.
 */
@RunWith(AndroidJUnit4.class)
public class TweetActivityTest implements JunitTestContract {
    private String TAG = "TweetActivityTest";

    @Rule
    public ActivityScenarioRule<TweetActivity> activityRule =
            new ActivityScenarioRule<>(TweetActivity.class);

    @Override
    public void prepare() {
        Log.i(TAG, "prepare");


    }

    /**
     * 测试加载Fragment
     */
    @Test
    public void testReplaceFragment() {
        Log.i(TAG, "testReplaceFragment");
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        activityRule.getScenario().recreate();
        activityRule.getScenario().onActivity(TweetActivity::showTweetFragment);


    }

    @Override
    public void release() {
        Log.i(TAG, "release");
    }
}
