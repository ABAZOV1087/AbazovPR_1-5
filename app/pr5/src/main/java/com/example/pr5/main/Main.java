package com.example.pr5.main;

import com.example.pr5.model.Book;
import com.example.pr5.model.Dissertation;
import com.example.pr5.model.Loan;
import com.example.pr5.model.Magazine;
import com.example.pr5.model.Reader;
import com.example.pr5.service.Library;
import com.example.pr5.ui.ConsoleUI;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {
        }

        Library library = new Library();
        library.getPublications().loadPublicationsFromCsv("publications.csv");

        if (library.getPublications().size() == 0) {
            seedDemoPublications(library);
            try {
                library.getPublications().savePublicationsToCsv("publications.csv");
            } catch (IOException e) {
                System.err.println("Preduprezhdenie: ne udalos' sohranit' nachal'nyj CSV: " + e.getMessage());
            }
        }

        seedDemoReadersAndLoans(library);

        ConsoleUI ui = new ConsoleUI(library);
        ui.run();
    }

    private static void seedDemoPublications(Library lib) {
        lib.addPublication(new Book("B1", "Chistyj kod", "Martin R.", 2019, "978-5-4461-0960-9", 464));
        lib.addPublication(new Book("B2", "Effektivnaya Java", "Bloh Dzh.", 2020, "978-5-907144-01-9", 460));
        lib.addPublication(new Book("B3", "Priemy OOP proektirovaniya", "Gamma E.", 2018, "978-5-459-01720-5", 368));
        lib.addPublication(new Book("B4", "Iskusstvo programmirovaniya", "Knut D.", 2015, "978-5-8459-0080-7", 720));
        lib.addPublication(new Book("B5", "Refaktoring koda", "Fauler M.", 2021, "978-5-907144-55-2", 448));
        lib.addPublication(new Book("B6", "Grokam algoritmy", "Bhargava A.", 2022, "978-5-4461-0923-4", 288));

        lib.addPublication(new Magazine("M1", "Nauka i zhizn'", "Redakciya NiZh", 2026, 9, "Sentyabr'"));
        lib.addPublication(new Magazine("M2", "Kvantik", "MCNMO", 2026, 10, "Oktyabr'"));
        lib.addPublication(new Magazine("M3", "Mir PK", "Otkrytye sistemy", 2025, 12, "Dekabr'"));

        lib.addPublication(new Dissertation("D1", "Metody verifikacii PO", "Sokolov I.V.", 2023, "05.13.11 Matematicheskoe i PO EVM", "Kandidatskaya"));
        lib.addPublication(new Dissertation("D2", "Raspredelennye SUBD", "Belova E.A.", 2021, "05.13.15 Vychislitel'nye mashiny", "Doktorskaya"));
        lib.addPublication(new Dissertation("D3", "Neirosetevye kompilyatory", "Gromov A.N.", 2024, "05.13.18 Matematicheskoe modelirovanie", "Kandidatskaya"));
    }

    private static void seedDemoReadersAndLoans(Library lib) {
        lib.registerReader(new Reader("R1", "Ivanov Sergej Petrovich", "+370-611-10203"));
        lib.registerReader(new Reader("R2", "Lazareva Anna Dmitrievna", "+370-611-20304"));
        lib.registerReader(new Reader("R3", "Mel'nikov Pavel Olegovich", "+370-611-30405"));
        lib.registerReader(new Reader("R4", "Romanova Kseniya Andreevna", "+370-611-40506"));
        lib.registerReader(new Reader("R5", "Tarasov Viktor Igorevich", "+370-611-50607"));

        try {

            lib.issuePublication("R1", "B1", LocalDate.of(2026, 10, 10)); // srok 2026-10-24 (prosrochena)
            lib.issuePublication("R2", "M1", LocalDate.of(2026, 10, 20)); // srok 2026-10-27 (prosrochena)


            Loan activeLoan = lib.issuePublication("R3", "B2", LocalDate.of(2026, 11, 5));
            lib.extendLoan(activeLoan.getLoanId(), 7);
            lib.issuePublication("R1", "B6", LocalDate.of(2026, 11, 8));


            lib.reserveOccupiedPublication("R4", "B1");
        } catch (Exception e) {
            System.err.println("Oshibka inicializacii testovyh vydach: " + e.getMessage());
        }
    }
}
