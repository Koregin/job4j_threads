package ru.job4j.io;

import java.io.*;

public final class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (OutputStream out = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(out)) {
            byte[] buffer = content.getBytes();
            bos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
