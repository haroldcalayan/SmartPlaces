package com.pipalapipapalapi.smartplaces.database;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public final class MainThreadExecutor implements Executor {

    private Handler mainThread;

    private static MainThreadExecutor instance = new MainThreadExecutor();

    private MainThreadExecutor() {
        mainThread = new Handler(Looper.getMainLooper());
    }

    public static MainThreadExecutor getInstance() {
        return instance;
    }

    @Override
    public void execute(Runnable command) {
        mainThread.post(command);
    }
}
