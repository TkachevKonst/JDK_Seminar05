package ru.gb.jdk;

import java.util.concurrent.CountDownLatch;

public class Table extends Thread {

    private final int PHILOSOPHER_COUNT = 5;

    private Philosopher[] philosophers;

    private Tableware[] tablewares;

    private CountDownLatch cdl;

    public Table() {
        philosophers = new Philosopher[PHILOSOPHER_COUNT];
        tablewares = new Tableware[PHILOSOPHER_COUNT];
        cdl = new CountDownLatch(PHILOSOPHER_COUNT);
        tableFormation();
    }

    private void tableFormation() {
        for (int i = 0; i < PHILOSOPHER_COUNT; i++) {
            tablewares[i] = new Tableware();
        }
        for (int i = 0; i < PHILOSOPHER_COUNT; i++) {
            philosophers[i] = new Philosopher("№ " + (i+1), i, (i + 1) % PHILOSOPHER_COUNT, this, cdl);
        }
    }

    @Override
    public void run() {
        System.out.println("Все сели за стол");
        try {
            thoughtProcess();
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Все поели вдоволь");
    }

    public synchronized boolean toGetTableware(int leftTableware, int rightTableware) {
        if (!tablewares[leftTableware].isUsing() && !tablewares[rightTableware].isUsing()) {
            tablewares[leftTableware].setUsing(true);
            tablewares[rightTableware].setUsing(true);
            return true;
        }
        return false;
    }


    public void putTableware(int leftTableware, int rightTableware) {
        tablewares[leftTableware].setUsing(false);
        tablewares[rightTableware].setUsing(false);
    }

    private void thoughtProcess() {
        for (Philosopher philosopher : philosophers) {
            philosopher.start();
        }
    }


}
