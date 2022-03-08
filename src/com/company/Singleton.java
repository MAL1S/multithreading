package com.company;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static Singleton instance;

    long timer;

    //locks for thread scopes to adjust their work
    final Object lock1 = new Object();
    final Object lock2 = new Object();

    final int N = 300;

    volatile List<Integer> buffer1 = new ArrayList<Integer>();
    volatile List<Double> buffer2 = new ArrayList<Double>();

    //booleans to find out that thread is currently active or dead
    volatile Boolean isFirst = false;
    volatile Boolean isSecond = false;
    volatile Boolean isThird = false;

    //getting singleton instance
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
