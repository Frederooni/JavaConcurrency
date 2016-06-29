package com.sdgsystems.java.concurrency;

import static com.sdgsystems.java.concurrency.Common.*;

/**
 * Uses notify() and wait() and busy-waiting.
 */
public class Attempt5 {

    public final static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
    }

    static volatile GreatThing sharedGreatThing = null;
    static Object syncObject = new Object();

    static class Producer extends Thread {
        public void run() {
            while (true) {
                sharedGreatThing = nextGreatThing();
                // Tell consumer it’s ready
                synchronized (syncObject) {
                    syncObject.notify();
                }
                // Wait for consumption
                synchronized (syncObject) {
                    try {
                        while (sharedGreatThing != null) {
                            syncObject.wait();
                        }
                    } catch (InterruptedException e) { }
                }
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            while (true) {
                // Wait for sharedGreatThing
                synchronized (syncObject) {
                    try {
                        while (sharedGreatThing == null) {
                            syncObject.wait();
                        }
                    } catch (InterruptedException e) { }
                }
                useGreatThing(sharedGreatThing);
                // Tell producer we have consumed it
                sharedGreatThing = null;
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }
        }
    }
}
