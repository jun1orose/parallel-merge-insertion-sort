import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool implements Runnable {

    private final int threadLimit;
    private AtomicInteger freeThreads;
    private BlockingQueue<Task> taskQueue;
    private boolean terminated = false;

    public ThreadPool(int nThreads) {
        this.threadLimit = nThreads;
        this.freeThreads = new AtomicInteger(nThreads);

        Comparator<Task> priorityCompare = Comparator.comparingInt(t -> t.priority);
        this.taskQueue = new PriorityBlockingQueue<>(nThreads, priorityCompare);
    }

    void addTask(Task task) {
        if (!isTerminated()) {
            taskQueue.add(task);
        }
    }

    void taskStarted() {
        freeThreads.decrementAndGet();
    }

    void taskFinished() {
        freeThreads.incrementAndGet();
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (this.freeThreads.get() != 0) {
                    Task newTask = taskQueue.poll(1, TimeUnit.SECONDS);
                    if (newTask != null) {
                        newTask.start();
                    } else {
                        if (terminated) {

                        } else {

                        }
                    }
                }
                waitUntilTerminated();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        terminated = true;
    }

    private void waitUntilTerminated() throws InterruptedException {
        while (!terminated) {
            wait();
        }
    }

    boolean isTerminated() {
        return terminated;
    }
}