package com.sdgsystems.java.concurrency;

import static com.sdgsystems.java.concurrency.Common.*;
import java.util.concurrent.CountDownLatch;

/**
 * Uses CountDownLatch for synchronization.
 */
public class Attempt6 {

    public final static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
    }

    static volatile GreatThing sharedGreatThing = null;
    static CountDownLatch ready = new CountDownLatch(1);
    static CountDownLatch consumed = new CountDownLatch(1);

    static class Producer extends Thread {
        public void run() {
            while (true) {
                sharedGreatThing = nextGreatThing();
                // Tell consumer it’s ready
                ready.countDown();
                // Wait for consumption
                try {
                    consumed.await();
                } catch (InterruptedException e) { }
                // Reset latch
                consumed = new CountDownLatch(1);
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            while (true) {
                // Wait for sharedGreatThing
                try {
                    ready.await();
                } catch (InterruptedException e) { }
                // Reset latch
                ready = new CountDownLatch(1);
                useGreatThing(sharedGreatThing);
                // Tell producer we have consumed it
                consumed.countDown();
            }
        }
    }
}
