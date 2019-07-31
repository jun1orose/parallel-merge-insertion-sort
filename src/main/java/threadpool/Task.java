class Task extends Thread {
    private ThreadPool threadPool;
    private Runnable action;
    final int priority;

    Task(ThreadPool threadPool, int priority, Runnable action) {
        this.threadPool = threadPool;
        this.priority = priority;
        this.action = action;
    }

    @Override
    public void run() {
        threadPool.taskStarted();
        action.run();
        threadPool.taskFinished();

        if (threadPool.isTerminated()) {
            threadPool.notify();
        }
    }
}