package com.frame.androidlibrary.http;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ThreadPoolManage {
    private static ThreadPoolManage threadPoolManage;

    private ThreadPoolExecutor threadPoolExecutor;

    private LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();

    private ThreadPoolManage() {
        threadPoolExecutor = new ThreadPoolExecutor(4, 20, 15, TimeUnit.SECONDS,
                linkedBlockingQueue, rejectedExecutionHandler);
        new Thread(mainRunnable).start();
    }

    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (null != r) {
                linkedBlockingQueue.add(r);
            }
        }
    };

    public static ThreadPoolManage getInstance() {
        if (null == threadPoolManage) {
            synchronized (ThreadPoolManage.class) {
                if (null == threadPoolManage) {
                    threadPoolManage = new ThreadPoolManage();

                }
            }
        }
        return threadPoolManage;
    }


    public void execute(Runnable runnable) {
        if (null != runnable)
            linkedBlockingQueue.add(runnable);
    }

    Runnable mainRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (null != threadPoolExecutor) {
                    try {
                        com.orhanobut.logger.Logger.d("准备执行...");
                        threadPoolExecutor.execute(linkedBlockingQueue.take());
                        com.orhanobut.logger.Logger.d("执行完成...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("null == threadPoolExecutor");
                }
            }

        }
    };


}
