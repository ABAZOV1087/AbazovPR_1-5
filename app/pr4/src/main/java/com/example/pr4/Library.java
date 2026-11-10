package com.example.pr4;

import java.util.Arrays;


public class Library {
    private Book[] books;
    private int bookSize;

    private Reader[] readers;
    private int readerSize;

    public Library() {
        this.books = new Book[6];
        this.bookSize = 0;
        this.readers = new Reader[4];
        this.readerSize = 0;
    }

    private void expandBooksIfNeeded() {
        if (bookSize == books.length) {
            books = Arrays.copyOf(books, books.length * 2);
        }
    }

    private void expandReadersIfNeeded() {
        if (readerSize == readers.length) {
            readers = Arrays.copyOf(readers, readers.length * 2);
        }
    }

    public void addBook(Book book) throws LibraryOperationException {
        if (findBookById(book.getId()) != null) {
            throw new LibraryOperationException("Kniga s inventarnym nomerom #" + book.getId() + " uzhe zaregistrirovana.");
        }
        expandBooksIfNeeded();
        books[bookSize++] = book;
    }

    public void registerReader(Reader reader) throws LibraryOperationException {
        if (findReaderById(reader.getReaderId()) != null) {
            throw new LibraryOperationException("Chitatel' s biletom #" + reader.getReaderId() + " uzhe sushchestvuet.");
        }
        expandReadersIfNeeded();
        readers[readerSize++] = reader;
    }

    public Book findBookById(int id) {
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getId() == id) {
                return books[i];
            }
        }
        return null;
    }

    public Reader findReaderById(int id) {
        for (int i = 0; i < readerSize; i++) {
            if (readers[i].getReaderId() == id) {
                return readers[i];
            }
        }
        return null;
    }

    public void updateBookInfo(int id, String newTitle, String newAuthor, int newYear) throws LibraryOperationException {
        Book target = findBookById(id);
        if (target == null) {
            throw new LibraryOperationException("Kniga s ID #" + id + " ne najdena dlya redaktirovaniya.");
        }
        target.setTitle(newTitle);
        target.setAuthor(newAuthor);
        target.setPublicationYear(newYear);
    }

    public void removeBook(int id) throws LibraryOperationException {
        int index = -1;
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new LibraryOperationException("Kniga s ID #" + id + " ne najdena.");
        }
        if (!books[index].isAvailable()) {
            throw new LibraryOperationException("Nel'zya spisat' knigu #" + id + ", poka ona nahoditsya na rukah u chitatelya.");
        }
        for (int i = index; i < bookSize - 1; i++) {
            books[i] = books[i + 1];
        }
        books[--bookSize] = null;
    }

    public void issueBook(int bookId, int readerId) throws LibraryOperationException {
        Book book = findBookById(bookId);
        if (book == null) {
            throw new LibraryOperationException("Kniga #" + bookId + " ne najdena v kataloge.");
        }
        Reader reader = findReaderById(readerId);
        if (reader == null) {
            throw new LibraryOperationException("Chitatel' s biletom #" + readerId + " ne zaregistrirovan.");
        }
        if (!book.isAvailable()) {
            throw new LibraryOperationException("Kniga \"" + book.getTitle() + "\" uzhe vydana chitatelyu #" + book.getBorrowedByReaderId());
        }
        book.setBorrowedByReaderId(readerId);
    }

    public void returnBook(int bookId) throws LibraryOperationException {
        Book book = findBookById(bookId);
        if (book == null) {
            throw new LibraryOperationException("Kniga #" + bookId + " ne chislitsya v biblioteke.");
        }
        if (book.isAvailable()) {
            throw new LibraryOperationException("Kniga \"" + book.getTitle() + "\" uzhe nahoditsya v fonde biblioteki.");
        }
        book.setBorrowedByReaderId(-1);
    }

    public Book[] getBooksByReader(int readerId) throws LibraryOperationException {
        if (findReaderById(readerId) == null) {
            throw new LibraryOperationException("Chitatel' #" + readerId + " ne najden.");
        }
        int count = 0;
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getBorrowedByReaderId() == readerId) count++;
        }
        Book[] result = new Book[count];
        int pos = 0;
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getBorrowedByReaderId() == readerId) {
                result[pos++] = books[i];
            }
        }
        return result;
    }

    public Book[] searchByTitleOrAuthor(String query) {
        String q = query.trim().toLowerCase();
        int count = 0;
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getTitle().toLowerCase().contains(q)
                    || books[i].getAuthor().toLowerCase().contains(q)) {
                count++;
            }
        }
        Book[] matches = new Book[count];
        int pos = 0;
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getTitle().toLowerCase().contains(q)
                    || books[i].getAuthor().toLowerCase().contains(q)) {
                matches[pos++] = books[i];
            }
        }
        return matches;
    }

    public void sortBooksByYearAscending() {
        for (int i = 0; i < bookSize - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < bookSize; j++) {
                if (books[j].getPublicationYear() < books[minIdx].getPublicationYear()) {
                    minIdx = j;
                }
            }
            Book temp = books[i];
            books[i] = books[minIdx];
            books[minIdx] = temp;
        }
    }

    public Book[] getAllBooks() {
        return Arrays.copyOf(books, bookSize);
    }

    public Reader[] getAllReaders() {
        return Arrays.copyOf(readers, readerSize);
    }
}
