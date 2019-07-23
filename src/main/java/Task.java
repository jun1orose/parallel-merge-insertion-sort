class AbstractTask extends Thread {
    private ThreadPool threadPool;
    private Runnable action;
    final int priority;

    AbstractTask(ThreadPool threadPool, int priority, Runnable action) {
        this.threadPool = threadPool;
        this.priority = priority;
        this.action = action;
    }

    @Override
    public void run() {
        threadPool.taskStarted();
        action.run();
        threadPool.taskFinished();
    }
}