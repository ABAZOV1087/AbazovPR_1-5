package com.example.pr5.model;
import com.example.pr5.exception.ValidationException;


public abstract class Publication implements FinePolicy {
    private String inventoryCode;
    private String title;
    private String author;
    private int year;
    private boolean available;
    private int totalBorrows;
    private String[] reservationQueue;
    private int queueSize;

    public Publication(String inventoryCode, String title, String author, int year) {
        setInventoryCode(inventoryCode);
        setTitle(title);
        setAuthor(author);
        setYear(year);
        this.available = true;
        this.totalBorrows = 0;
        this.reservationQueue = new String[4];
        this.queueSize = 0;
    }

    public abstract PublicationCategory getCategory();

    public abstract String getSpecificMetadata();

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        if (inventoryCode == null || inventoryCode.trim().isEmpty()) {
            throw new ValidationException("Inventarnyj nomer ne mozhet byt' pustym.");
        }
        this.inventoryCode = inventoryCode.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Nazvanie izdaniya ne mozhet byt' pustym.");
        }
        this.title = title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new ValidationException("Avtor ne mozhet byt' pustym.");
        }
        this.author = author.trim();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1450 || year > 2030) {
            throw new ValidationException("Nekorrektnyj god izdaniya: " + year);
        }
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getTotalBorrows() {
        return totalBorrows;
    }

    public void setTotalBorrows(int totalBorrows) {
        this.totalBorrows = Math.max(0, totalBorrows);
    }

    public void incrementBorrows() {
        this.totalBorrows++;
    }


    public void enqueueReader(String readerId) {
        for (int i = 0; i < queueSize; i++) {
            if (reservationQueue[i].equals(readerId)) {
                throw new ValidationException("Chitatel' " + readerId + " uzhe stoit v ocheredi na eto izdanie.");
            }
        }
        if (queueSize == reservationQueue.length) {
            String[] grown = new String[reservationQueue.length * 2];
            for (int i = 0; i < queueSize; i++) {
                grown[i] = reservationQueue[i];
            }
            reservationQueue = grown;
        }
        reservationQueue[queueSize++] = readerId;
    }

    public String pollNextReservedReader() {
        if (queueSize == 0) return null;
        String first = reservationQueue[0];
        for (int i = 0; i < queueSize - 1; i++) {
            reservationQueue[i] = reservationQueue[i + 1];
        }
        reservationQueue[--queueSize] = null;
        return first;
    }

    public int getQueueSize() {
        return queueSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Publication)) return false;
        Publication other = (Publication) obj;
        return inventoryCode.equals(other.inventoryCode);
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-18s | %-28s | %-18s | %4d g. | %-10s | Vydach: %2d | Ochered': %d | %s",
                inventoryCode,
                getCategory().getTitle(),
                title,
                author,
                year,
                available ? "Dostupno" : "Na rukah",
                totalBorrows,
                queueSize,
                getSpecificMetadata());
    }
}
