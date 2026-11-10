package com.example.pr5.storage;

import com.example.pr5.model.Book;
import com.example.pr5.model.Dissertation;
import com.example.pr5.model.EntityComparator;
import com.example.pr5.model.Magazine;
import com.example.pr5.model.Publication;
import com.example.pr5.model.PublicationCategory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


public class ArrayRepository {
    private Object[] items;
    private int count;

    public ArrayRepository() {
        this.items = new Object[16];
        this.count = 0;
    }

    public void add(Object element) {
        if (count == items.length) {
            items = Arrays.copyOf(items, items.length * 2);
        }
        items[count++] = element;
    }

    public Object get(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Indeks vne granic hranilischa: " + index);
        }
        return items[index];
    }

    public int size() {
        return count;
    }

    public void clear() {
        Arrays.fill(items, 0, count, null);
        count = 0;
    }


    public void sort(EntityComparator comparator) {
        for (int i = 1; i < count; i++) {
            Object key = items[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(items[j], key) > 0) {
                items[j + 1] = items[j];
                j--;
            }
            items[j + 1] = key;
        }
    }

    public void savePublicationsToCsv(String filePath) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                if (items[i] instanceof Publication) {
                    Publication p = (Publication) items[i];
                    String field1 = "";
                    String field2 = "";
                    if (p instanceof Book) {
                        Book b = (Book) p;
                        field1 = b.getIsbn();
                        field2 = String.valueOf(b.getPageCount());
                    } else if (p instanceof Magazine) {
                        Magazine m = (Magazine) p;
                        field1 = String.valueOf(m.getIssueNumber());
                        field2 = m.getMonthName();
                    } else if (p instanceof Dissertation) {
                        Dissertation d = (Dissertation) p;
                        field1 = d.getAcademicField();
                        field2 = d.getDegreeLevel();
                    }
                    pw.printf("%s;%s;%s;%s;%d;%b;%d;%s;%s%n",
                            p.getCategory().name(),
                            p.getInventoryCode(),
                            p.getTitle(),
                            p.getAuthor(),
                            p.getYear(),
                            p.isAvailable(),
                            p.getTotalBorrows(),
                            field1,
                            field2);
                }
            }
        }
    }

    public void loadPublicationsFromCsv(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    String[] cols = line.split(";");
                    if (cols.length < 9) {
                        System.err.println("[Propusk povrezhdennoj stroki CSV]: " + line);
                        continue;
                    }
                    PublicationCategory cat = PublicationCategory.valueOf(cols[0].trim());
                    String code = cols[1].trim();
                    String title = cols[2].trim();
                    String author = cols[3].trim();
                    int year = Integer.parseInt(cols[4].trim());
                    boolean avail = Boolean.parseBoolean(cols[5].trim());
                    int borrows = Integer.parseInt(cols[6].trim());

                    Publication pub;
                    if (cat == PublicationCategory.BOOK) {
                        pub = new Book(code, title, author, year, cols[7].trim(), Integer.parseInt(cols[8].trim()));
                    } else if (cat == PublicationCategory.MAGAZINE) {
                        pub = new Magazine(code, title, author, year, Integer.parseInt(cols[7].trim()), cols[8].trim());
                    } else {
                        pub = new Dissertation(code, title, author, year, cols[7].trim(), cols[8].trim());
                    }
                    pub.setAvailable(avail);
                    pub.setTotalBorrows(borrows);
                    add(pub);
                } catch (Exception rowEx) {
                    System.err.println("[Oshibka razbora stroki CSV]: " + line + " (" + rowEx.getMessage() + ")");
                }
            }
        } catch (IOException ioEx) {
            System.err.println("[Oshibka chteniya fajla " + filePath + "]: " + ioEx.getMessage());
        }
    }
}
