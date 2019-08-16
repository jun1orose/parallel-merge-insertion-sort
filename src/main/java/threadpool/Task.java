package threadpool;

public abstract class Task implements Runnable {
    int priority;

    private TaskCounter relatedTaskCounter;
    private TaskCounter childTaskCounter;

    public Task(int priority, TaskCounter countOfRelatedTasks, TaskCounter childTaskCounter) {
        this.priority = priority;
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

    void increasePriority() {

    }
}