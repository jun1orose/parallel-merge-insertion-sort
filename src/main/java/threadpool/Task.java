package threadpool;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Task implements Runnable {

    final int priority;
    private final static AtomicLong seq = new AtomicLong();
    long seqNum;

    private TaskCounter relatedTaskCounter;
    private TaskCounter childTaskCounter;

    public Task(int priority, TaskCounter countOfRelatedTasks, TaskCounter childTaskCounter) {
        this.priority = priority;
        this.seqNum = Task.seq.getAndIncrement();

        this.relatedTaskCounter = countOfRelatedTasks;
        this.childTaskCounter = childTaskCounter;
    }

    protected abstract void solve();

    @Override
    public void run() {
        this.solve();
        this.relatedTaskCounter.decrement();
    }

    boolean isAvailableForExecuting() {
        return childTaskCounter.getCounter() <= 0;
    }

    void increaseSeqNum() {
        this.seqNum = Task.seq.getAndIncrement();
    }
}