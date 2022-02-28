package com.company;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static final Object lock1 = new Object();
    static final Object lock2 = new Object();

    static final int N = 300;

    static volatile List<Integer> buffer1 = new ArrayList<Integer>();
    static volatile List<Double> buffer2 = new ArrayList<Double>();

    static volatile Boolean isFirst = false;
    static volatile Boolean isSecond = false;
    static volatile Boolean isThird = false;

    public static void main(String[] args) throws InterruptedException {
        startFirstThread();
        startSecondThread();

        Thread.sleep(1000);

        startThirdThread();
    }

    public static void startFirstThread() {
        Thread thread = new MyThread1();
        thread.start();
    }

    public static void startSecondThread() {
        Thread thread = new MyThread2();
        thread.start();
    }

    public static void startThirdThread() {
        Thread thread = new MyThread3();
        thread.start();
    }

    public static class MyThread1 extends Thread {

        private  int counter = 1000;

        @Override
        public void run() {
            System.out.println("first start");
            isFirst = true;
            while (counter > 0) {

                synchronized (lock1) {
                    buffer1.add(counter);
                    System.out.println("= " + buffer1.size() + " " + buffer1);
                    counter--;

                    if (buffer1.size() == N) {
                        try {
                            lock1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("first end");
            isFirst = false;
        }
    }

    public static class MyThread2 extends Thread {

        private final int index = 0;

        @Override
        public void run() {
            System.out.println("second start");
            isSecond = true;
            while (buffer1.size() != 0 || isFirst) {
                if (buffer1.size() == 0) continue;

                synchronized (lock2) {


                    synchronized (lock1) {
                        var b = buffer1.get(index);
                        //System.out.println(b);
                        var a = Math.tan(b);
                        buffer2.add(a);
                        System.out.println("== " + buffer2.size() + " " + buffer2);
                        buffer1.remove(index);
                        lock1.notify();
                    }

                    if (buffer2.size() == N) {
                        try {
                            lock2.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("second end");
            isSecond = false;
        }
    }

    public static class MyThread3 extends Thread {

        private final int index = 0;

        @Override
        public void run() {
            System.out.println("third start");
            isThird = true;
            int counter = 0;
            while (buffer2.size() != 0 || isSecond) {
                if (buffer2.size() == 0) continue;

                synchronized (lock2) {
                    //System.out.println("arctan(" + buffer2.get(index) + ") = " + Math.atan(buffer2.get(index)) + " " + counter++);
                    System.out.println("=== " + counter++ + " " + Math.atan(buffer2.get(index)));
                    buffer2.remove(index);
                    lock2.notify();
                }
            }
            System.out.println("third end");
            isThird = false;
        }
    }
}