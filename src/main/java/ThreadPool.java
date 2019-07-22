import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool extends Thread {

    private AtomicInteger freeThreads;
    private LinkedBlockingQueue<Runnable> taskQueue;

    public ThreadPool() {
        this.freeThreads = new AtomicInteger(6);
        this.taskQueue = new LinkedBlockingQueue<Runnable>();
    }

    void addTask(Runnable action) {
        taskQueue.add(action);
    }

    void threadStarted() {
        freeThreads.decrementAndGet();
    }

    void threadFinished() {
        freeThreads.incrementAndGet();
    }

    @Override
    public void run() {
        try {
            Runnable action = taskQueue.poll(1, TimeUnit.SECONDS);
            new Thread(action).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}