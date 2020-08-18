package com.lixin.thoughtworkshomework;

import org.junit.After;
import org.junit.Before;

/**
 * @author lixin
 * @date 2020/8/18.
 */
interface JunitTestContract {
    @Before
    void prepare();

    @After
    void release();
}
