package com.example.mp4;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.ListenableWorker.Result;
import java.util.concurrent.TimeUnit;

public class MyWorker extends Worker {
    static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Фоновая задача выполняется");

        // Имитация длительной работы (5 секунд)
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        Log.d(TAG, "Фоновая задача завершена");
        return Result.success();
    }
}