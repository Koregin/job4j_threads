package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        StringBuilder sb = new StringBuilder();
        try (BufferedInputStream bufInputStream = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = bufInputStream.read()) != -1) {
                if (filter.test((char) data)) {
                    sb.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
