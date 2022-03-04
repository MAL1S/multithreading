package com.company;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //starting all threads
        startFirstThread();
        startSecondThread();
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
}