package cn.udday.lnyxp;

import android.content.Context;

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

    private HttpTask[] httpTasks;

    private static volatile HttpThreadPool threadPool;

    private BlockingQueue<BitmapRequest> queue;
    private Context context;
    public HttpThreadPool(Context context) {
        this.context = context;
        queue = new LinkedBlockingQueue<>();
        stopThread();
        initTread();
    }

    private void initTread() {
        httpTasks = new HttpTask[CORE_POOL_SIZE];
        for (int i = 0; i < CORE_POOL_SIZE; i++) {
            HttpTask thread = new HttpTask(queue,context);
            thread.start();
            httpTasks[i] = thread;
        }
    }

    private void stopThread() {
        if (httpTasks != null && httpTasks.length > 0) {
            for (HttpTask thread : httpTasks) {
                if (!thread.isInterrupted()) {
                    thread.interrupt();
                }
            }
        }
    }
    public void addRequest(BitmapRequest request){
        if(!queue.contains(request)){
            queue.add(request);
        }
    }
    public static HttpThreadPool getInstance(Context context) {
        if (threadPool == null) {
            synchronized (HttpThreadPool.class) {
                if (threadPool == null) {
                    threadPool = new HttpThreadPool(context);
                }
            }
        }
        return threadPool;
    }
}
