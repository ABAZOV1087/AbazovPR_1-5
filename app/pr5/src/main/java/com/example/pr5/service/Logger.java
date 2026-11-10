package com.example.pr5.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


public class Logger {
    private static String[] entries = new String[16];
    private static int size = 0;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void info(String message) {
        String record = "[" + LocalDateTime.now().format(FMT) + "] " + message;
        if (size == entries.length) {
            entries = Arrays.copyOf(entries, entries.length * 2);
        }
        entries[size++] = record;

        try (PrintWriter out = new PrintWriter(new FileWriter("log.txt", true))) {
            out.println(record);
        } catch (IOException e) {
            System.err.println("Ne udalos' zapisat' sobytie v log.txt: " + e.getMessage());
        }
    }

    public static String[] getEntries() {
        return Arrays.copyOf(entries, size);
    }
}
