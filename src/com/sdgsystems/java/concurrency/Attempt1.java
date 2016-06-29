package com.sdgsystems.java.concurrency;

import static com.sdgsystems.java.concurrency.Common.*;

/**
 * Uses busy-waiting but eventually stops making progress because each thread
 * sees a different value for sharedGreatThing.
 */
public class Attempt1 {

    public final static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
    }

    static GreatThing sharedGreatThing = null;

    static class Producer extends Thread {
        public void run() {
            while (true) {
                sharedGreatThing = nextGreatThing();
                // Wait for consumption
                while (sharedGreatThing != null) { }
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            while (true) {
                // Wait for sharedGreatThing
                while (sharedGreatThing == null) { }
                useGreatThing(sharedGreatThing);
                // Tell producer we have consumed it
                sharedGreatThing = null;
            }
        }
    }
}
