package org.demo.webserver.helpers.logging;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Logger {

    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(20);
    private volatile boolean stayAlive = true;
    private Thread thread;

    public Logger() {
        (thread = new Thread(() -> {
            try {

                while (stayAlive) {
                    System.out.println(queue.take());
                    /**
                    StackFrame caller = StackWalker.getInstance().walk(frames -> frames.skip(1).findFirst().orElseThrow());
		            System.out.printf("    [DEBUG | %-12s] %s%n", caller.getMethodName(), message);
                    **/
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
    }

    public void log(String log) {
        try {
            queue.put(log + " on thread " + Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            stayAlive = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
