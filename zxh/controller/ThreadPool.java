package controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1000, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
    private static ThreadPool instance = null;
    public static ThreadPool getThreadPool() {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null) {
                    instance = new ThreadPool();
                }
            }
        }
        return instance;
    }
    private ThreadPool() {
    }
    public void shutdown() {
        executor.shutdown();
    }
    public void executor(Runnable runnable) {
        executor.execute(runnable);
    }
}
