package com.company;

public class MyThread1 extends Thread {

    private int counter = 1000;

    static Singleton instance = Singleton.getInstance();

    @Override
    public void run() {
        System.out.println("first start");
        instance.isFirst = true;
        while (counter > 0) { //while accomplishing task condition

            //lock to prevent situations when one thread add value to buffer while another doesn't know about it
            synchronized (instance.lock1) {
                instance.buffer1.add(counter);
                System.out.println("= " + instance.buffer1.size() + " " + instance.buffer1);
                counter--;

                if (instance.buffer1.size() <= instance.N) {
                    try {
                        instance.lock1.wait(); //pause thread1 to work with other threads in one time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("first end");
        instance.isFirst = false;
    }
}