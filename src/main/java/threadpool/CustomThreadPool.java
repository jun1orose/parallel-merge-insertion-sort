package threadpool;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool implements Runnable {

    private AtomicInteger freeThreads;
    private BlockingQueue<Task> taskQueue;
    private volatile boolean terminated;

    ThreadPool(int nThreads) {
        this.freeThreads = new AtomicInteger(nThreads);

        Comparator<Task> priorityCompare = Comparator.comparingInt(t -> t.priority);
        this.taskQueue = new PriorityBlockingQueue<>(nThreads, priorityCompare);
    }

    void addTask(Task task) {
        if (!terminated) {
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
                if (!terminated || (taskQueue.size() == 0)) {
                    if (freeThreads.get() > 0) {
                        Task newTask = taskQueue.poll(1, TimeUnit.SECONDS);
                        if (newTask != null) {
                            newTask.start();
                        } else {
                            waitNewTask();
                        }
                    } else {
                        waitFreeThread();
                    }
                } else {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        terminated = true;
    }

    private synchronized void waitNewTask() throws InterruptedException {
        while (taskQueue.size() == 0) {
            this.wait();
        }
    }

    private synchronized void waitFreeThread() throws InterruptedException {
        while (freeThreads.get() == 0) {
            this.wait();
        }
    }

    private synchronized void wakeUp() {
        this.notifyAll();
    }
}