package com.example.pr4;

import java.util.Scanner;

public class Main {
    private static final Library library = new Library();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedInitialData();

        boolean running = true;
        while (running) {
            printMenu();
            int option = promptInt("Vash vybor: ");

            try {
                switch (option) {
                    case 1:
                        displayAllBooks();
                        break;
                    case 2:
                        displayAllReaders();
                        break;
                    case 3:
                        addBookDialog();
                        break;
                    case 4:
                        editBookDialog();
                        break;
                    case 5:
                        deleteBookDialog();
                        break;
                    case 6:
                        issueBookDialog();
                        break;
                    case 7:
                        returnBookDialog();
                        break;
                    case 8:
                        showReaderBooksDialog();
                        break;
                    case 9:
                        searchBooksDialog();
                        break;
                    case 10:
                        library.sortBooksByYearAscending();
                        System.out.println("Katalog otsortirovan po godu izdaniya (po vozrastaniyu).");
                        displayAllBooks();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Rabota s bibliotechnoj sistemoj zavershena.");
                        break;
                    default:
                        System.out.println("Neizvestnaya komanda menyu. Povtorite vvod.");
                }
            } catch (LibraryOperationException e) {
                System.out.println("[OSHIBKA OPERACII]: " + e.getMessage());
            }
        }
    }

    private static void seedInitialData() {
        try {
            library.registerReader(new Reader(101, "Smirnov Aleksej Viktorovich", "+370-600-11223"));
            library.registerReader(new Reader(102, "Kovalyova Mariya Igorevna", "+370-600-44556"));
            library.registerReader(new Reader(103, "Orlov Denis Sergeevich", "+370-600-77889"));

            library.addBook(new Textbook(1, "Algoritmy i struktury dannyh", "Kormen T.", 2013, "Informatika", 2));
            library.addBook(new Novel(2, "Master i Margarita", "Bulgakov M.A.", 1967, "Misticheskij roman"));
            library.addBook(new Textbook(3, "Linejnaya algebra", "Il'in V.A.", 2008, "Vysshaya matematika", 1));
            library.addBook(new Novel(4, "Prestuplenie i nakazanie", "Dostoevskij F.M.", 1866, "Psihologicheskaya drama"));
            library.addBook(new Novel(5, "Dyuna", "Gerbert F.", 1965, "Nauchnaya fantastika"));

            library.issueBook(2, 101);
            library.issueBook(3, 102);
        } catch (LibraryOperationException e) {
            System.out.println("Oshibka predzagruzki: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n========= AIS \"BIBLIOTEKA\" (PR #4 — Variant 1) =========");
        System.out.println("1. Pokazat' vse knigi (polimorfnyj vyvod)");
        System.out.println("2. Pokazat' spisok chitatelej");
        System.out.println("3. Dobavit' novuyu knigu");
        System.out.println("4. Izmenit' dannye knigi");
        System.out.println("5. Udalit' (spisat') knigu");
        System.out.println("6. Vydat' knigu chitatelyu");
        System.out.println("7. Prinyat' vozvrat knigi");
        System.out.println("8. Spisok knig na rukah u chitatelya");
        System.out.println("9. Poisk knigi po nazvaniyu ili avtoru");
        System.out.println("10. Otsortirovat' knigi po godu izdaniya");
        System.out.println("0. Vyhod");
    }

    private static int promptInt(String label) {
        while (true) {
            System.out.print(label);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Oshibka vvoda! Trebuetsya celoe chislo.");
            }
        }
    }

    private static void displayAllBooks() {
        Book[] catalog = library.getAllBooks();
        System.out.println("\n--- Fond biblioteki (" + catalog.length + " sht.) ---");
        for (Book b : catalog) {
            System.out.println(b);
        }
    }

    private static void displayAllReaders() {
        Reader[] list = library.getAllReaders();
        System.out.println("\n--- Zaregistrirovannye chitateli ---");
        for (Reader r : list) {
            System.out.println(r);
        }
    }

    private static void addBookDialog() throws LibraryOperationException {
        int id = promptInt("Inventarnyj ID: ");
        System.out.print("Nazvanie knigi: ");
        String title = scanner.nextLine().trim();
        System.out.print("Avtor: ");
        String author = scanner.nextLine().trim();
        int year = promptInt("God izdaniya: ");
        int type = promptInt("Kategoriya (1 — Uchebnik, 2 — Hudozhestvennyj roman): ");

        if (type == 1) {
            System.out.print("Disciplina: ");
            String subject = scanner.nextLine().trim();
            int course = promptInt("Nomer kursa: ");
            library.addBook(new Textbook(id, title, author, year, subject, course));
        } else {
            System.out.print("Literaturnyj zhanr: ");
            String genre = scanner.nextLine().trim();
            library.addBook(new Novel(id, title, author, year, genre));
        }
        System.out.println("Kniga uspeshno dobavlena v katalog.");
    }

    private static void editBookDialog() throws LibraryOperationException {
        int id = promptInt("Vvedite ID knigi dlya izmeneniya: ");
        System.out.print("Novoe nazvanie: ");
        String title = scanner.nextLine().trim();
        System.out.print("Novyj avtor: ");
        String author = scanner.nextLine().trim();
        int year = promptInt("Novyj god izdaniya: ");
        library.updateBookInfo(id, title, author, year);
        System.out.println("Kartochka knigi obnovlena.");
    }

    private static void deleteBookDialog() throws LibraryOperationException {
        int id = promptInt("Vvedite ID knigi dlya spisaniya: ");
        library.removeBook(id);
        System.out.println("Kniga udalena iz fonda.");
    }

    private static void issueBookDialog() throws LibraryOperationException {
        int bookId = promptInt("ID vydaemoj knigi: ");
        int readerId = promptInt("Nomer chitatel'skogo bileta: ");
        library.issueBook(bookId, readerId);
        System.out.println("Vydacha knigi uspeshno zaregistrirovana.");
    }

    private static void returnBookDialog() throws LibraryOperationException {
        int bookId = promptInt("ID vozvrashchaemoj knigi: ");
        library.returnBook(bookId);
        System.out.println("Kniga vozvrashchena v fond biblioteki.");
    }

    private static void showReaderBooksDialog() throws LibraryOperationException {
        int readerId = promptInt("Vvedite nomer chitatel'skogo bileta: ");
        Book[] held = library.getBooksByReader(readerId);
        if (held.length == 0) {
            System.out.println("Za dannym chitatelem net chislyashchihsya knig.");
        } else {
            System.out.println("Knigi na rukah u chitatelya #" + readerId + ":");
            for (Book b : held) {
                System.out.println("  • " + b);
            }
        }
    }

    private static void searchBooksDialog() {
        System.out.print("Vvedite fragment nazvaniya ili familiyu avtora: ");
        String query = scanner.nextLine();
        Book[] found = library.searchByTitleOrAuthor(query);
        if (found.length == 0) {
            System.out.println("Sovpadenij ne najdeno.");
        } else {
            System.out.println("Najdeno pozicij (" + found.length + "):");
            for (Book b : found) {
                System.out.println("  • " + b);
            }
        }
    }
}
