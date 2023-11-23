package com.ptu.devCloud.constants;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommonConstants {

    // 使用cpu核心数的两倍作为线程池size
    public static int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    public static final ThreadPoolExecutor COMMON_THREAD_POOL = new ThreadPoolExecutor(
            THREAD_POOL_SIZE,
            THREAD_POOL_SIZE,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    // SUCCESS 字符串
    public static final String COMMON_SUCCESS_STRING = "SUCCESS";

    // jwt 加密密钥
    public static final String SECRET_KEY = "SECRET_KEY";

    // 忽略权限
    public static final String IGNORE_PERMISSION = "ignorePermission";

}
