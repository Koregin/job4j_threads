package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] planes = {"\\", "|", "/"};
        int counter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (counter == planes.length) {
                counter = 0;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                /**/
            }
            System.out.print("\r Loading ... " + planes[counter]);
            counter++;
        }
    }
}
