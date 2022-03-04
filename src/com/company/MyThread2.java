package com.company;

public class MyThread2 extends Thread {

    private final int index = 0;

    static Singleton instance = Singleton.getInstance();

    @Override
    public void run() {
        System.out.println("second start");
        instance.isSecond = true;
        while (instance.buffer1.size() != 0 || instance.isFirst) {
            //skip if buffer1 is empty
            if (instance.buffer1.size() == 0) continue;

            //to synchronize work with thread3
            //similar to interaction with thread1
            //to prevent different knowledge about determined buffer
            synchronized (instance.lock2) {

                //to prevent removing element while thread1 doesn't know about it
                synchronized (instance.lock1) {
                    var b = instance.buffer1.get(index);
                    //System.out.println(b);
                    var a = Math.tan(b);
                    instance.buffer2.add(a);
                    System.out.println("== " + instance.buffer2.size() + " " + instance.buffer2);
                    instance.buffer1.remove(index);
                    instance.lock1.notify(); //wake up thread1 when removing element from it
                }

                if (instance.buffer2.size() <= instance.N) {
                    try {
                        instance.lock2.wait(); //pause thread2 to work with other threads in one time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("second end");
        instance.isSecond = false;
    }
}
