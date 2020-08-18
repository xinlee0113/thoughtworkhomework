package com.lixin.thoughtworkshomework.stub;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lixin.library.base.aroute.Constants;
import com.lixin.library.base.aroute.RouterPath;

/**
 * @author lixin
 * 入口activity
 */
@Route(path = RouterPath.Activity.ENTRY)
public class EntryActivity extends Activity {

    private static final String TAG = "EntryActivity";

    @Autowired(name = Constants.EntryParams.KEY)
    int entryCode = Constants.EntryParams.Code.DEFAULT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        handleIntent(intent);
    }

    private void handleIntent(@NonNull Intent intent) {
        // 对非scheme跳转做兼容处理
        if (entryCode == Constants.EntryParams.Code.DEFAULT) {
            entryCode = intent.getIntExtra(Constants.EntryParams.KEY, Constants.EntryParams.Code.MOMENTS);
        }
        String routerPath = "";
        switch (entryCode) {
            case Constants.EntryParams.Code.MOMENTS:
                routerPath = RouterPath.Activity.MOMENTS;
                break;
            default:
                break;

        }
        ARouter.getInstance()
                .build(Uri.parse(String.format(RouterPath.BASE_URI_STRING, routerPath)))
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .with(intent.getExtras())
                .navigation(this, new NavCallback() {
                    @Override
                    public void onLost(Postcard postcard) {
                        Log.e(TAG, "onLost, postcard: " + postcard);

                        finish();
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.d(TAG, "onArrival, postcard: " + postcard);

                        finish();
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        Log.e(TAG, "onInterrupt, postcard: " + postcard);

                        finish();
                    }
                });
    }
}
