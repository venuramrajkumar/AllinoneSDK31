package com.raj.javaprogramming;

public class EvenOddThread {

    public static void main(String[] args) {

        EvenThread evenThread = new EvenThread();
        evenThread.start();

        OddThread oddThread = new OddThread();
        oddThread.start();
    }

}

class EvenThread extends Thread {

    static final String evenLock = "evenLock";

    @Override
    public void run() {
        super.run();

        for (int i = 0; i < 10; i++) {
            int number = i;
            synchronized (evenLock) {
                while (number % 2 == 0) {
                    try {
                        evenLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " " + number);
                    number++;
                }


                evenLock.notifyAll();
            }


        }

    }
}

class OddThread extends Thread {

    static final String oddLock = "evenLock";

    @Override
    public void run() {
        super.run();

        for (int i = 0; i < 10; i++) {
            int number = i;
            synchronized (oddLock) {
                while (number % 2 == 1) {
                    try {
                        oddLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " " + number);
                    number++;
                }

                oddLock.notifyAll();
            }


        }

    }

}

