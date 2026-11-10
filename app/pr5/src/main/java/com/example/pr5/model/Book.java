package com.example.pr5.model;

public class Book extends Publication {
    private String isbn;
    private int pageCount;

    public Book(String inventoryCode, String title, String author, int year, String isbn, int pageCount) {
        super(inventoryCode, title, author, year);
        this.isbn = (isbn == null || isbn.trim().isEmpty()) ? "N/A" : isbn.trim();
        this.pageCount = Math.max(1, pageCount);
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public PublicationCategory getCategory() {
        return PublicationCategory.BOOK;
    }

    @Override
    public double calcFine(int overdueDays) {
        if (overdueDays <= 0) return 0.0;
        return overdueDays * getCategory().getBaseDailyRate();
    }

    @Override
    public String getSpecificMetadata() {
        return "ISBN: " + isbn + ", стр.: " + pageCount;
    }
}

