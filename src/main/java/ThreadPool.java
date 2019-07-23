import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool implements Runnable {

    private AtomicInteger freeThreads;
    private BlockingQueue<Runnable> taskQueue;

    public ThreadPool(int nThreads) {
        this.freeThreads = new AtomicInteger(nThreads);
        this.taskQueue = new PriorityBlockingQueue<Runnable>();
    }

    void addTask(Runnable action) {
        taskQueue.add(action);
    }

    void taskStarted() {
        freeThreads.decrementAndGet();
    }

    void taskFinished() {
        freeThreads.incrementAndGet();
    }

    public void run() {
        try {
            Runnable action = taskQueue.poll(1, TimeUnit.SECONDS);
            if (action != null) {
                new Thread(action).start();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}