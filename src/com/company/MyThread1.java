package com.company;

public class MyThread1 extends Thread {

    private int counter = 1000;

    static Singleton instance = Singleton.getInstance();

    @Override
    public void run() {
        System.out.println("first start");
        instance.timer = System.currentTimeMillis();
        instance.isFirst = true;
        while (counter > 0) { //while accomplishing task condition

            if (instance.buffer1.size() >= instance.N) {
                try {
                    instance.lock1.wait();
                } catch (Exception e) {

                }
            } else {
                //lock to prevent situations when one thread add value to buffer while another doesn't know about it
                synchronized (instance.lock1) {
                    instance.buffer1.add(counter);
                    System.out.println("= " + instance.buffer1.size() + " " + instance.buffer1);
                    counter--;
                }
            }
        }
        System.out.println("first end");
        instance.isFirst = false;
    }
}