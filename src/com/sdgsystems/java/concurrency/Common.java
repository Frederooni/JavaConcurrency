package com.sdgsystems.java.concurrency;

import java.util.Random;

public class Common {
    static int createCount = 0, useCount = 0;
    static Random random = new Random();
    static boolean verifyOrder = true;

    static GreatThing nextGreatThing() {
        GreatThing thing = new GreatThing(++createCount);
        System.out.println("Produced " + thing);
        work(random.nextInt(1000));
        return thing;
    }

    static void useGreatThing(GreatThing thing) {
        if (verifyOrder && thing.id != ++useCount) {
            System.out.println("ERROR! Consumer got " + thing
                    + " instead of GreatThing number " + useCount + "!");
            System.exit(1);
        }
        System.out.println("Consumed " + thing);
        work(random.nextInt(1000));
    }

    static double work(long ms) {
        double sum = 0;
        for (long i = 0L; i < ms; i++) {
            sum += Math.sqrt(i);
        }
        return sum;
    }

    static class GreatThing {
        final int id;
        public GreatThing(int id) {
            this.id = id;
        }
        public String toString() {
            return "GreatThing number " + id;
        }
    }
}
