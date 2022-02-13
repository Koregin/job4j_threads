package ru.job4j.thread;

import java.io.*;
import java.net.URL;
import java.sql.Timestamp;

public class Wget implements Runnable {
    private static final int BUFFERSIZE = 1024;
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("downloaded.file")) {
            byte[] dataBuffer = new byte[BUFFERSIZE];
            int bytesRead = 0;
            do {
                Timestamp timeStart = new Timestamp(System.currentTimeMillis());
                bytesRead = in.read(dataBuffer, 0, BUFFERSIZE);
                Timestamp timeFinish = new Timestamp(System.currentTimeMillis());
                long timeDiff = timeFinish.getTime() - timeStart.getTime(); /*Время потраченное на скачивание 1024 байт*/
                if (timeDiff > 0) {
                    double currentSpeed = BUFFERSIZE / (timeDiff / 1000.0); /*вычисляем текущую скорость*/
                    System.out.println("Current speed: " + currentSpeed);
                    double speedDiff = currentSpeed - (speed); /*Разница скоростей заданной и текущей в мс*/
                    System.out.println("Разница в скорости: " + speedDiff);
                    if (speedDiff > 0) {
                        double pause = (BUFFERSIZE / speedDiff) * 1000; /*Вычисляем паузу задержки*/
                        System.out.println("Задержка = " + pause);
                        Thread.sleep((long) pause);
                    }
                    if (bytesRead != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                }
            } while (bytesRead != -1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
<<<<<<< HEAD
        wget.join();
=======
        //wget.join();
>>>>>>> origin/master
    }
}
