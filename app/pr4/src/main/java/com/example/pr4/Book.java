package com.example.pr4;


public abstract class Book {
    private int id;
    private String title;
    private String author;
    private int publicationYear;
    private int borrowedByReaderId;

    public Book(int id, String title, String author, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.borrowedByReaderId = -1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getBorrowedByReaderId() {
        return borrowedByReaderId;
    }

    public void setBorrowedByReaderId(int borrowedByReaderId) {
        this.borrowedByReaderId = borrowedByReaderId;
    }

    public boolean isAvailable() {
        return borrowedByReaderId == -1;
    }


    public abstract String getEditionDetails();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        String status = isAvailable() ? "V nalichii" : "Vydana (Chitatel' ID: " + borrowedByReaderId + ")";
        return String.format("#%d | «%s» — %s (%d g.) | %s | [%s]",
                id, title, author, publicationYear, getEditionDetails(), status);
    }
}
