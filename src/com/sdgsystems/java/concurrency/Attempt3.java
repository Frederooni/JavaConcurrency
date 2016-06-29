package com.sdgsystems.java.concurrency;

import static com.sdgsystems.java.concurrency.Common.*;

/**
 * Uses busy-waiting with a small delay.
 */
public class Attempt3 {

    public final static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
    }

    static volatile GreatThing sharedGreatThing = null;

    static class Producer extends Thread {
        public void run() {
            while (true) {
                sharedGreatThing = nextGreatThing();
                // Wait for consumption
                while (sharedGreatThing != null) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) { }
                }
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            while (true) {
                // Wait for sharedGreatThing
                while (sharedGreatThing == null) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) { }
                }
                useGreatThing(sharedGreatThing);
                // Tell producer we have consumed it
                sharedGreatThing = null;
            }
        }
    }
}
