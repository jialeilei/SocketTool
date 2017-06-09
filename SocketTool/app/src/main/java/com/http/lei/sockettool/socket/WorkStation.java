package com.http.lei.sockettool.socket;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lei on 2017/6/9.
 */
public class WorkStation {
    private static final int CORE_POOL_SIZE = 1;
    private static final int MAXI_MUM_POOL_SIZE = 1;

    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXI_MUM_POOL_SIZE, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {
        AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("threadName: "+integer.getAndIncrement());
            return thread;
        }
    });


    public static void add(Runnable r){
        poolExecutor.execute(r);
    }

}
