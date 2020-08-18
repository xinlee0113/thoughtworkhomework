package com.lixin.thoughtworkshomework.ui.tweetlist;

import androidx.fragment.app.testing.FragmentScenario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.annotation.LooperMode;

/**
 * @author lixin
 * @date 2020/8/17.
 */
@RunWith(MockitoJUnitRunner.class)
@LooperMode(LooperMode.Mode.PAUSED)
public class TweetFragmentTest {
    FragmentScenario<TweetFragment> fragmentScenario;

    @Test
    public void launchFragmentAndVerifyUI() {
        // use launchInContainer to launch the fragment with UI

        // now use espresso to look for the fragment's text view and verify it is displayed
    }

    @Before
    public void init() {
        fragmentScenario = FragmentScenario.launch(TweetFragment.class);

    }

    /**
     * 测试界面绑定数据
     */
    @Test
    public void testBindData() {
        fragmentScenario.onFragment(TweetFragment::bindTweetDataToView);
    }

    /**
     * 测试滚动RecycleView，分段加载数据
     */
    public void scollTweetList() {
        //just for show ui test
    }
}
