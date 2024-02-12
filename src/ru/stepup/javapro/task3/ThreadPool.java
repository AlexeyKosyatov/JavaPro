package src.ru.stepup.javapro.task3;

import java.util.LinkedList;

public class ThreadPool {
    private LinkedList<Runnable> queue;
    private WorkerThread[] workers;

    private boolean isShutdown = false;

    public ThreadPool(int capacity) {
        this.queue = new LinkedList<>();
        this.workers = new WorkerThread[capacity];

        for (int i = 0; i < capacity; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            if (isShutdown) {
                throw new IllegalStateException("Пул потоков остановлен");
            }

            queue.addLast(task);
            queue.notifyAll();
        }
    }

    public void shutdown() {
        isShutdown = true;
        synchronized (queue) {
            queue.notifyAll();
        }

        for (WorkerThread worker : workers) {
            worker.interrupt();
        }
    }

    public void awaitTermination() {
        for (WorkerThread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }

                    task = queue.removeFirst();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}