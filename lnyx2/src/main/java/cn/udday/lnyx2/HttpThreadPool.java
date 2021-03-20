package cn.udday.lnyx2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpThreadPool {
    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static final int LIVE_TIME = 10;

    private static volatile HttpThreadPool threadPool;

    private BlockingQueue<Future<?>> queue = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor executor;

    public static HttpThreadPool getInstance() {
        if (threadPool == null) {
            synchronized (HttpThreadPool.class) {
                if (threadPool == null) {
                    threadPool = new HttpThreadPool();
                }
            }
        }
        return threadPool;
    }

    private HttpThreadPool() {
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE+1, LIVE_TIME , TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectHandler);
        executor.execute(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask<?> task = null;
                try {
                    task = (FutureTask<?>) queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    executor.execute(task);
                }
            }
        }
    };

    public synchronized Response submit(Callable<Response> task) throws ExecutionException, InterruptedException {
        if (task == null) throw new NullPointerException("task == null , 无法执行");
        Future<Response> future = executor.submit(task);
        return future.get();
    }

    public void execute(FutureTask<?> task) {
        if (task == null) throw new NullPointerException("task == null , 无法执行");
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    RejectedExecutionHandler rejectHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                queue.put(new FutureTask<>(runnable, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
