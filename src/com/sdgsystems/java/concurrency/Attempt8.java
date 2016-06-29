package com.sdgsystems.java.concurrency;

import static com.sdgsystems.java.concurrency.Common.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Uses a thread pool of Producer-Consumer runnables.
 */
public class Attempt8 {

    public final static void main(String[] args) {
        verifyOrder = false;
        int numThreads = 10;
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        useGreatThing(nextGreatThing());
                    }
                }
            });
        }
    }
}
