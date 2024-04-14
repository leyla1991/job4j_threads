package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream input = new FileInputStream(file)) {
            int data;
            while ((data = input.read()) > 0) {
              if (filter.test((char) data)) {
                  output.append((char) data);
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return content(character -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(character -> character < 0x80);
    }
}