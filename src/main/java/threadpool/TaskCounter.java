package threadpool;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TaskCounter {
    private Lock lock;
    private volatile int counter;

    TaskCounter(int counter) {
        this.lock = new ReentrantLock();
        this.counter = counter;
    }

    void decrement() {
        lock.lock();
        try {
            this.counter = this.counter - 1;
        } finally {
            lock.unlock();
        }
    }

    int getCounter() {
        lock.lock();
        try {
            return this.counter;
        } finally {
            lock.unlock();
        }
    }
}
