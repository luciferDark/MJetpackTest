package com.ll.mjetpacktest.startup;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;
import androidx.work.Configuration;
import androidx.work.WorkManager;

/**
 * @Auther kylin
 * @Data 2020/8/21 - 23:29
 * @Package com.ll.mjetpacktest.startup
 * @Description
 */
public class KylinInitializer implements Initializer<WorkManager> {
    private static final String TAG = "KylinInitializer";

    @NonNull
    @Override
    public WorkManager create(@NonNull Context context) {
        Log.d(TAG, "create: KylinInitializer");
        Configuration configuration = new Configuration.Builder().build();
        WorkManager.initialize(context, configuration);
        return WorkManager.getInstance(context);
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
