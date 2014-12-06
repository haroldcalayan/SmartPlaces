package com.pipalapipapalapi.smartplaces.database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class BackgroundThreadExecutor implements Executor {

    private Executor backgroundThread;

    private static BackgroundThreadExecutor instance = new BackgroundThreadExecutor();

    private BackgroundThreadExecutor() {
        backgroundThread = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {

            @Override
            public Thread newThread(final Runnable r) {
                return new Thread(new Runnable() {
                    @Override public void run() {
                        android.os.Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);
                        r.run();
                    }
                }, "BackgroundThreadExecutor");
            }
        });
    }

    public static BackgroundThreadExecutor getInstance() {
        return instance;
    }

    @Override
    public void execute(Runnable command) {
        backgroundThread.execute(command);
    }
}
