package com.example.pr5.ui;

import com.example.pr5.exception.LimitExceededException;
import com.example.pr5.exception.PublicationNotAvailableException;
import com.example.pr5.exception.ValidationException;
import com.example.pr5.model.Book;
import com.example.pr5.model.Dissertation;
import com.example.pr5.model.Loan;
import com.example.pr5.model.Magazine;
import com.example.pr5.model.Publication;
import com.example.pr5.service.Library;
import com.example.pr5.service.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleUI {
    private final Library library;
    private final Scanner scanner;
    private final LocalDate systemDate;

    public ConsoleUI(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
        this.systemDate = LocalDate.of(2026, 11, 10);
    }

    public void run() {
        while (true) {
            System.out.println("\n================ BIBLIOTECHNAYA SISTEMA (PR №5 — Variant 1) ================");
            System.out.println("Tekuschaya rabochaya data: " + systemDate);
            System.out.println("1. Katalog izdanij i poisk (podmenyu)");
            System.out.println("2. Operacii vydachi, vozvrata, prodleniya i rezerva (podmenyu)");
            System.out.println("3. Analiticheskie otchety i istoriya chitatelya (podmenyu)");
            System.out.println("4. Fajlovoe hranilische (CSV) i zhurnal sobytij log.txt");
            System.out.println("0. Vyhod iz programmy");
            System.out.print("Viberite razdel: ");

            String cmd = scanner.nextLine().trim();
            try {
                switch (cmd) {
                    case "1":
                        catalogSubmenu();
                        break;
                    case "2":
                        circulationSubmenu();
                        break;
                    case "3":
                        reportsSubmenu();
                        break;
                    case "4":
                        persistenceSubmenu();
                        break;
                    case "0":
                        System.out.println("Rabota zavershena. Do svidaniya!");
                        return;
                    default:
                        System.out.println("Neizvestnyj punkt menyu. Vvedite cifru ot 0 do 4.");
                }
            } catch (ValidationException | LimitExceededException e) {
                System.out.println("\n[BIZNES-OGRANICHENIE]: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[OSHIBKA]: " + e.getMessage());
            }
        }
    }

    private void catalogSubmenu() {
        System.out.println("\n--- Razdel 1: Katalog izdanij ---");
        System.out.println("1. Pokazat' vse izdaniya fonda");
        System.out.println("2. Poisk po podstroke nazvaniya ili avtoru");
        System.out.println("3. Sortirovat' po godu izdaniya (vstavkami)");
        System.out.println("4. Sortirovat' po nazvaniyu (vstavkami)");
        System.out.println("5. Zaregistrirovat' novoe izdanie");
        System.out.println("6. Pokazat' spisok vseh chitatelej");
        System.out.print("Vash vybor: ");
        String sub = scanner.nextLine().trim();

        switch (sub) {
            case "1":
                printAllPublications();
                break;
            case "2":
                System.out.print("Vvedite poiskovyj zapros: ");
                Publication[] found = library.searchPublications(scanner.nextLine());
                if (found.length == 0) {
                    System.out.println("Nichego ne najdeno.");
                } else {
                    for (Publication p : found) System.out.println(p);
                }
                break;
            case "3":
                library.sortByYear();
                printAllPublications();
                break;
            case "4":
                library.sortByTitle();
                printAllPublications();
                break;
            case "5":
                createPublicationDialog();
                break;
            case "6":
                for (int i = 0; i < library.getReaders().size(); i++) {
                    System.out.println(library.getReaders().get(i));
                }
                break;
            default:
                System.out.println("Vozvrat v glavnoe menyu.");
        }
    }

    private void circulationSubmenu() {
        System.out.println("\n--- Razdel 2: Vydacha, vozvrat i rezervirovanie ---");
        System.out.println("1. Vydat' izdanie chitatelyu");
        System.out.println("2. Prinyat' vozvrat izdaniya (s raschetom shtrafa i proverkoj ocheredi)");
        System.out.println("3. Prodlit' srok vydachi (ne bolee 2 raz)");
        System.out.println("4. Zarezervirovat' zanyatoe izdanie v ochered' (Uslozhnenie)");
        System.out.println("5. Pokazat' vse zaregistrirovannye vydachi");
        System.out.print("Vash vybor: ");
        String sub = scanner.nextLine().trim();

        try {
            switch (sub) {
                case "1":
                    System.out.print("ID chitatelya (naprimer, R1): ");
                    String rId = scanner.nextLine().trim();
                    System.out.print("Inventarnyj kod izdaniya (naprimer, B1): ");
                    String pCode = scanner.nextLine().trim();
                    Loan loan = library.issuePublication(rId, pCode, systemDate);
                    System.out.println("Oformlena vydacha: " + loan);
                    break;
                case "2":
                    System.out.print("Vvedite kod vydachi (naprimer, LN-101): ");
                    String loanId = scanner.nextLine().trim();
                    double fine = library.returnPublication(loanId, systemDate);
                    System.out.printf("Izdanie vozvrascheno. Nachislennyj shtraf: %.2f rub.%n", fine);
                    break;
                case "3":
                    System.out.print("Vvedite kod vydachi (naprimer, LN-101): ");
                    String extLoanId = scanner.nextLine().trim();
                    int days = readSafeInt("Na skol'ko dnej prodlit' (1..30): ");
                    library.extendLoan(extLoanId, days);
                    System.out.println("Srok vozvrata uspeshno prodlen!");
                    break;
                case "4":
                    System.out.print("ID chitatelya: ");
                    String qReader = scanner.nextLine().trim();
                    System.out.print("Kod zanyatogo izdaniya: ");
                    String qPub = scanner.nextLine().trim();
                    library.reserveOccupiedPublication(qReader, qPub);
                    System.out.println("Chitatel' postavlen v ochered' rezervirovaniya.");
                    break;
                case "5":
                    for (int i = 0; i < library.getLoans().size(); i++) {
                        System.out.println(library.getLoans().get(i));
                    }
                    break;
                default:
                    System.out.println("Vozvrat v glavnoe menyu.");
            }
        } catch (PublicationNotAvailableException e) {
            System.out.println("\n[OTKAZ V VYDACHE]: " + e.getMessage());
        }
    }

    private void reportsSubmenu() {
        System.out.println("\n--- Razdel 3: Analiticheskie otchety ---");
        System.out.println("1. Otchet «Dolzhniki» s polimorfnym raschetom shtrafov");
        System.out.println("2. Rejting «Top-5 populyarnyh izdanij»");
        System.out.println("3. Svodnaya tablica po kategoriyam fonda");
        System.out.println("4. Istoriya vydach konkretnogo chitatelya");
        System.out.print("Vash vybor: ");
        String sub = scanner.nextLine().trim();

        switch (sub) {
            case "1":
                library.printDebtorsReport(systemDate);
                break;
            case "2":
                library.printTopFivePopularReport();
                break;
            case "3":
                library.printCategorySummaryReport();
                break;
            case "4":
                System.out.print("Vvedite ID chitatelya (naprimer, R1): ");
                library.printReaderHistory(scanner.nextLine().trim());
                break;
            default:
                System.out.println("Vozvrat v glavnoe menyu.");
        }
    }

    private void persistenceSubmenu() {
        System.out.println("\n--- Razdel 4: Rabota s fajlami i logami ---");
        System.out.println("1. Sohranit' katalog izdanij v publications.csv");
        System.out.println("2. Perezagruzit' katalog iz publications.csv");
        System.out.println("3. Pokazat' zapisi operativnogo zhurnala Logger");
        System.out.print("Vash vybor: ");
        String sub = scanner.nextLine().trim();

        switch (sub) {
            case "1":
                try {
                    library.getPublications().savePublicationsToCsv("publications.csv");
                    System.out.println("Dannye uspeshno sohraneny v fajl publications.csv.");
                } catch (IOException e) {
                    System.out.println("Oshibka sohraneniya fajla: " + e.getMessage());
                }
                break;
            case "2":
                library.getPublications().clear();
                library.getPublications().loadPublicationsFromCsv("publications.csv");
                System.out.println("Katalog zagruzhen iz fajla. Vsego pozicij: " + library.getPublications().size());
                break;
            case "3":
                String[] logs = Logger.getEntries();
                System.out.println("\n--- Zhurnal dejstvij (vsego zapisej: " + logs.length + ") ---");
                for (String entry : logs) {
                    System.out.println(entry);
                }
                break;
            default:
                System.out.println("Vozvrat v glavnoe menyu.");
        }
    }

    private void createPublicationDialog() {
        System.out.print("Tip (1 — Kniga, 2 — Zhurnal, 3 — Dissertaciya): ");
        String t = scanner.nextLine().trim();
        System.out.print("Inventarnyj kod: ");
        String code = scanner.nextLine().trim();
        System.out.print("Nazvanie: ");
        String title = scanner.nextLine().trim();
        System.out.print("Avtor / Izdatel'stvo: ");
        String author = scanner.nextLine().trim();
        int year = readSafeInt("God vypuska: ");
        if ("1".equals(t)) {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine().trim();
            int pages = readSafeInt("Kolichestvo stranic: ");
            library.addPublication(new Book(code, title, author, year, isbn, pages));
        } else if ("2".equals(t)) {
            int issue = readSafeInt("Nomer vypuska: ");
            System.out.print("Mesyac vypuska: ");
            String month = scanner.nextLine().trim();
            library.addPublication(new Magazine(code, title, author, year, issue, month));
        } else if ("3".equals(t)) {
            System.out.print("Nauchnaya special'nost': ");
            String field = scanner.nextLine().trim();
            System.out.print("Uchenaya stepen' (kand./dokt.): ");
            String degree = scanner.nextLine().trim();
            library.addPublication(new Dissertation(code, title, author, year, field, degree));
        } else {
            System.out.println("Neizvestnyj tip izdaniya.");
            return;
        }
        System.out.println("Izdanie zaregistrirovano.");
    }

    private void printAllPublications() {
        System.out.println("\n--- Katalog izdanij (" + library.getPublications().size() + " ed.) ---");
        for (int i = 0; i < library.getPublications().size(); i++) {
            System.out.println(library.getPublications().get(i));
        }
    }

    private int readSafeInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Nekorrektnoe chislo! Poprobujte esche raz.");
            }
        }
    }
}