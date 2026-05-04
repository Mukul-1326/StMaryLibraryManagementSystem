package com.library.util;

public class ThreadUtil {

    // RUN TASK IN BACKGROUND
    public static void runAsync(Runnable task) {
        Thread thread = new Thread(task);
        thread.start();
    }

    // RUN WITH DELAY (SIMULATION / LOADING)
    public static void runWithDelay(Runnable task, int delayMillis) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
                task.run();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
                e.printStackTrace();
            }
        });
        thread.start();
    }

    // SAFE EXECUTION WITH ERROR HANDLING
    public static void runSafe(Runnable task) {
        Thread thread = new Thread(() -> {
            try {
                task.run();
            } catch (Exception e) {
                System.out.println("Error in background task");
                e.printStackTrace();
            }
        });
        thread.start();
    }
}