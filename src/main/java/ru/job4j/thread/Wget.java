package ru.job4j.thread;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;

public class Wget implements Runnable {
    private static final int BUFFERSIZE = 1024;
    private static final long ONESECOND = 1000;
    private final URL url;
    private final int speed;

    public Wget(String url, int speed) throws MalformedURLException {
        this.url = new URL(url);
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FilenameUtils.getName(url.getPath()))) {
            byte[] dataBuffer = new byte[BUFFERSIZE];
            int bytesRead;
            long byteCounter = 0;
            Timestamp timeStart = new Timestamp(System.currentTimeMillis());
            Timestamp timeGapStart = new Timestamp(System.currentTimeMillis());
            while ((bytesRead = in.read(dataBuffer, 0, BUFFERSIZE)) != -1) {
                byteCounter += bytesRead;
                if (byteCounter >= speed) {
                    byteCounter = 0;
                    long timeSpeedDownload = new Timestamp(System.currentTimeMillis()).getTime() - timeGapStart.getTime();
                    if (timeSpeedDownload < ONESECOND) {
                        long pause = ONESECOND - timeSpeedDownload;
                        System.out.println("Скорость выше указанной. Вносим задержку: " + pause);
                        Thread.sleep(pause);
                    }
                    timeGapStart = new Timestamp(System.currentTimeMillis());
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Всего времени на скачивание файла: "
                    + (new Timestamp(System.currentTimeMillis()).getTime() - timeStart.getTime()) / ONESECOND + " сек");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void argValidate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Error arguments. Arguments should be: wget <file_url> <download speed: bytes in sec>");
        }
    }

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        argValidate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
