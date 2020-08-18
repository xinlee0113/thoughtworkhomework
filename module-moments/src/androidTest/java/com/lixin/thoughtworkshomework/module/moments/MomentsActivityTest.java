package com.lixin.thoughtworkshomework.module.moments;

import android.util.Log;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.lixin.module.moments.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * @author lixin
 * @date 2020/8/18.
 */
@RunWith(AndroidJUnit4.class)
public class MomentsActivityTest implements JunitTestContract {
    private String TAG = "TweetActivityTest";

    @Rule
    public ActivityScenarioRule<com.lixin.thoughtworkshomework.module.moments.MomentsActivity> activityRule =
            new ActivityScenarioRule<>(com.lixin.thoughtworkshomework.module.moments.MomentsActivity.class);

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
        onView(ViewMatchers.withId(R.id.fragment_container)).check(matches(isDisplayed()));
        activityRule.getScenario().recreate();
        activityRule.getScenario().onActivity(com.lixin.thoughtworkshomework.module.moments.MomentsActivity::showTweetFragment);


    }

    @Override
    public void release() {
        Log.i(TAG, "release");
    }
}
