package com.company;

public class MyThread3 extends Thread {

    private final int index = 0;

    static Singleton instance = Singleton.getInstance();

    @Override
    public void run() {
        System.out.println("third start");
        instance.isThird = true;
        int counter = 0;
        while (instance.buffer2.size() != 0 || instance.isSecond || instance.isFirst) {
            if (instance.buffer2.size() == 0) continue;

            synchronized (instance.lock2) {
                System.out.println("=== " + counter++ + " " + Math.atan(instance.buffer2.get(index)));
                instance.buffer2.remove(index);
                instance.lock2.notify(); //wake up thread1 when removing element from it
            }
        }
        System.out.println("third end");
        instance.isThird = false;
    }
}
