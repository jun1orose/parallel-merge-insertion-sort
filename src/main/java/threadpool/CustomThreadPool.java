package threadpool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CustomThreadPool {

    private final BlockingQueue<Task> taskQueue;
    private final List<CustomThreadPool.Worker> workers;

    private volatile boolean terminated;

    public CustomThreadPool(int nThreads) {
        Comparator<Task> priorityCompare = (t1, t2) -> {
            int res = Integer.compare(t2.priority, t1.priority);
            if (res == 0) {
                res = (t1.seqNum < t2.seqNum ? -1 : 1);
            }
            return res;
        };

        this.taskQueue = new PriorityBlockingQueue<>(16, priorityCompare);
        this.workers = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            this.workers.add(new CustomThreadPool.Worker());
        }
        this.workers.forEach(Thread::start);
    }

    // not threadsafe
    public void addTask(Task task) {
        if (this.taskQueue.isEmpty()) {
            this.taskQueue.add(task);
            this.wakeUpWorkers();
        } else {
            this.taskQueue.add(task);
        }
    }

    public void terminate() {
        terminated = true;
    }

    public void awaitTerminating() {
        this.workers.forEach(worker -> {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void waitNewTask() throws InterruptedException {
        while (!this.terminated && this.taskQueue.isEmpty()) {
            this.wait();
        }
    }

    private synchronized void wakeUpWorkers() {
        if (!this.taskQueue.isEmpty()) {
            this.notifyAll();
        }
    }

    private final class Worker extends Thread {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (!CustomThreadPool.this.terminated || !CustomThreadPool.this.taskQueue.isEmpty()) {
                        Task newTask = CustomThreadPool.this.taskQueue.poll(100, TimeUnit.MILLISECONDS);
                        if (newTask != null) {
                            if (newTask.isAvailableForExecuting()) {
                                newTask.run();
                            } else {
                                newTask.increaseSeqNum();
                                CustomThreadPool.this.addTask(newTask);
                            }
                        } else {
                            CustomThreadPool.this.waitNewTask();
                        }
                    } else {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}