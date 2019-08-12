package threadpool;

public class Task extends Thread {
    private static CustomThreadPool customThreadPool;
    private Runnable action;
    final int priority;
    private TaskCounter countOfRelatedTasks = new TaskCounter(0);

    public Task(CustomThreadPool customThreadPool, Runnable action, int priority) {
        Task.customThreadPool = customThreadPool;
        this.priority = priority;
        this.action = action;
    }

    public Task(CustomThreadPool customThreadPool, Runnable action, int priority, TaskCounter countOfRelatedTasks) {
        this(customThreadPool, action, priority);
        this.countOfRelatedTasks = countOfRelatedTasks;
    }

    @Override
    public void run() {
        Task.customThreadPool.taskStarted();
        this.action.run();
        this.countOfRelatedTasks.decrement();
        Task.customThreadPool.taskFinished();
    }
}