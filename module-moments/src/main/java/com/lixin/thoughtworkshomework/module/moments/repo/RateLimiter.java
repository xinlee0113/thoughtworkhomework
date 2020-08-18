package com.lixin.thoughtworkshomework.module.moments.repo;

import android.os.SystemClock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author lixin
 * @date 2020/8/17.
 */
public class RateLimiter<KEY> {
    final Long timeout;
    final HashMap<KEY, Long> timestamps;

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
        timestamps = new HashMap<>();
    }

    synchronized boolean shouldFetch(KEY key) {
        Long lastFetched = timestamps.get(key);
        long now = SystemClock.uptimeMillis();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

    synchronized void reset(KEY key) {
        timestamps.remove(key);
    }
}

//
//class RateLimiter<in KEY>(timeout: Int, timeUnit: TimeUnit) {
//    private val timestamps = ArrayMap<KEY, Long>()
//    private val timeout = timeUnit.toMillis(timeout.toLong())
//

//
//    private fun now() = SystemClock.uptimeMillis()
//
//    @Synchronized
//    fun reset(key: KEY) {
//        timestamps.remove(key)
//    }
//}