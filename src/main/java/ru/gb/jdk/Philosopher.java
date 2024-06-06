package ru.gb.jdk;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {


    private Random random = new Random();

    private String name;

    private int leftTableware;

    private int rightTableware;

    private Table table;

    private int countEat;

    private CountDownLatch cdl;


    public Philosopher(String name, int leftTableware, int rightTableware, Table table, CountDownLatch cdl) {
        this.name = name;
        this.leftTableware = leftTableware;
        this.rightTableware = rightTableware;
        this.table = table;
        countEat = 0;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        while (countEat < 3) {
            try {
                eating();
                thinking();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(name + " поел и встал из-за стола");
        cdl.countDown();

    }

    public void eating() throws InterruptedException {
        if (table.toGetTableware(leftTableware, rightTableware)){
            countEat ++;
            System.out.println(name + " кушает");
            sleep(random.nextInt(3000, 7000));
            table.putTableware(leftTableware, rightTableware);
            System.out.println(name + " закончил прием пищи № " + countEat);
        }


    }


    public void thinking() throws InterruptedException {
        System.out.println(name + " размышляет, и ждет вилки");
        sleep(random.nextInt(1000,5000));
    }


}
