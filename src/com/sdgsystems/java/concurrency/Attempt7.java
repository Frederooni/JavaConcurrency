package com.sdgsystems.java.concurrency;

import static com.sdgsystems.java.concurrency.Common.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Uses ArrayBlockingQueue for synchronization.
 */
public class Attempt7 {

    public final static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
    }

    static ArrayBlockingQueue<GreatThing> queue =
            new ArrayBlockingQueue<GreatThing>(1);

    static class Producer extends Thread {
        public void run() {
            while (true) {
                try {
                    queue.put(nextGreatThing());
                } catch (InterruptedException e) { }
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            while (true) {
                try {
                    useGreatThing(queue.take());
                } catch (InterruptedException e) { }
            }
        }
    }
}
