package com.example.pr5.service;

import com.example.pr5.exception.LimitExceededException;
import com.example.pr5.exception.PublicationNotAvailableException;
import com.example.pr5.exception.ValidationException;
import com.example.pr5.model.EntityComparator;
import com.example.pr5.model.Loan;
import com.example.pr5.model.LoanState;
import com.example.pr5.model.Publication;
import com.example.pr5.model.Reader;
import com.example.pr5.storage.ArrayRepository;

import java.time.LocalDate;

public class Library {
    private static final int MAX_ACTIVE_LOANS_PER_READER = 5;
    private static final int MAX_EXTENSIONS = 2;

    private final ArrayRepository publications = new ArrayRepository();
    private final ArrayRepository readers = new ArrayRepository();
    private final ArrayRepository loans = new ArrayRepository();
    private final FineCalculator fineCalculator = new FineCalculator(15.0);

    public ArrayRepository getPublications() {
        return publications;
    }

    public ArrayRepository getReaders() {
        return readers;
    }

    public ArrayRepository getLoans() {
        return loans;
    }

    public void addPublication(Publication pub) {
        if (findPublicationByCode(pub.getInventoryCode()) != null) {
            throw new ValidationException("Izdanie s kodom " + pub.getInventoryCode() + " uzhe suschestvuet.");
        }
        publications.add(pub);
        Logger.info("Dobavleno izdanie: " + pub.getInventoryCode() + " «" + pub.getTitle() + "»");
    }

    public void registerReader(Reader reader) {
        if (findReaderById(reader.getReaderId()) != null) {
            throw new ValidationException("Chitatel' s biletom " + reader.getReaderId() + " uzhe zaregistrirovan.");
        }
        readers.add(reader);
        Logger.info("Zaregistrirovan chitatel': " + reader.getReaderId() + " (" + reader.getFullName() + ")");
    }

    public Publication findPublicationByCode(String code) {
        for (int i = 0; i < publications.size(); i++) {
            Publication p = (Publication) publications.get(i);
            if (p.getInventoryCode().equalsIgnoreCase(code)) {
                return p;
            }
        }
        return null;
    }

    public Reader findReaderById(String readerId) {
        for (int i = 0; i < readers.size(); i++) {
            Reader r = (Reader) readers.get(i);
            if (r.getReaderId().equalsIgnoreCase(readerId)) {
                return r;
            }
        }
        return null;
    }

    public Loan findLoanById(String loanId) {
        for (int i = 0; i < loans.size(); i++) {
            Loan l = (Loan) loans.get(i);
            if (l.getLoanId().equalsIgnoreCase(loanId)) {
                return l;
            }
        }
        return null;
    }

    public int countActiveLoansForReader(String readerId) {
        int active = 0;
        for (int i = 0; i < loans.size(); i++) {
            Loan l = (Loan) loans.get(i);
            if (l.getReaderId().equalsIgnoreCase(readerId) && !l.isReturned()) {
                active++;
            }
        }
        return active;
    }


    public Loan issuePublication(String readerId, String pubCode, LocalDate issueDate)
            throws PublicationNotAvailableException {
        Reader reader = findReaderById(readerId);
        if (reader == null) {
            throw new ValidationException("Chitatel' " + readerId + " ne najden.");
        }
        Publication pub = findPublicationByCode(pubCode);
        if (pub == null) {
            throw new ValidationException("Izdanie " + pubCode + " ne najdeno.");
        }

        if (!pub.getCategory().isTakeHomeAllowed()) {
            throw new PublicationNotAvailableException(
                    "Izdanie «" + pub.getTitle() + "» (" + pub.getCategory().getTitle()
                            + ") vydaetsya isklyuchitel'no v chital'nyj zal!");
        }

        if (!pub.isAvailable()) {
            throw new PublicationNotAvailableException(
                    "Izdanie «" + pub.getTitle() + "» sejchas na rukah. Vy mozhete oformit' rezervirovanie v ochered'.");
        }

        if (countActiveLoansForReader(readerId) >= MAX_ACTIVE_LOANS_PER_READER) {
            throw new LimitExceededException(
                    "Prevyshen limit vydachi! U chitatelya " + reader.getFullName() + " uzhe 5 aktivnyh izdanij na rukah.");
        }

        String loanId = "LN-" + (loans.size() + 101);
        LocalDate due = issueDate.plusDays(pub.getCategory().getStandardLoanDays());
        Loan loan = new Loan(loanId, reader.getReaderId(), pub.getInventoryCode(), issueDate, due);
        loans.add(loan);

        pub.setAvailable(false);
        pub.incrementBorrows();
        Logger.info("Vydano izdanie " + pub.getInventoryCode() + " chitatelyu " + reader.getReaderId() + " do " + due);
        return loan;
    }


    public void extendLoan(String loanId, int extraDays) {
        if (extraDays <= 0 || extraDays > 30) {
            throw new ValidationException("Kolichestvo dnej prodleniya dolzhno byt' ot 1 do 30.");
        }
        Loan loan = findLoanById(loanId);
        if (loan == null) {
            throw new ValidationException("Zapis' o vydache " + loanId + " ne najdena.");
        }
        if (loan.isReturned()) {
            throw new ValidationException("Nel'zya prodlit' uzhe zakrytuyu vydachu " + loanId + ".");
        }
        if (loan.getExtensionCount() >= MAX_EXTENSIONS) {
            throw new LimitExceededException("Otkaz: vydacha " + loanId + " uzhe prodlevalas' maksimal'noe chislo raz (2 raza).");
        }

        loan.setDueDate(loan.getDueDate().plusDays(extraDays));
        loan.incrementExtensions();
        Logger.info("Prodlena vydacha " + loanId + " na " + extraDays + " dn. (novyj srok: " + loan.getDueDate() + ")");
    }


    public double returnPublication(String loanId, LocalDate returnDate) throws PublicationNotAvailableException {
        Loan loan = findLoanById(loanId);
        if (loan == null) {
            throw new ValidationException("Vydacha s nomerom " + loanId + " ne najdena.");
        }
        if (loan.isReturned()) {
            throw new ValidationException("Izdanie po vydache " + loanId + " uzhe bylo vozvrascheno ranee.");
        }

        Publication pub = findPublicationByCode(loan.getPublicationCode());
        loan.setReturnDate(returnDate);
        loan.setState(LoanState.RETURNED);

        double fine = fineCalculator.computeLoanFine(loan, pub, returnDate);
        Logger.info("Vozvrat po vydache " + loanId + " (izdanie " + loan.getPublicationCode() + "). Nachislen shtraf: " + fine + " rub.");

        if (pub != null) {
            pub.setAvailable(true);

            while (pub.getQueueSize() > 0) {
                String nextReaderId = pub.pollNextReservedReader();
                if (nextReaderId != null && countActiveLoansForReader(nextReaderId) < MAX_ACTIVE_LOANS_PER_READER) {
                    Loan autoLoan = issuePublication(nextReaderId, pub.getInventoryCode(), returnDate);
                    Logger.info("Avtovydacha po ocheredi rezervirovaniya: izdanie " + pub.getInventoryCode()
                            + " naznacheno chitatelyu " + nextReaderId + " (vydacha " + autoLoan.getLoanId() + ")");
                    break;
                }
            }
        }
        return fine;
    }


    public void reserveOccupiedPublication(String readerId, String pubCode) {
        Reader r = findReaderById(readerId);
        if (r == null) throw new ValidationException("Chitatel' " + readerId + " ne najden.");
        Publication p = findPublicationByCode(pubCode);
        if (p == null) throw new ValidationException("Izdanie " + pubCode + " ne najdeno.");
        if (!p.getCategory().isTakeHomeAllowed()) {
            throw new ValidationException("Dissertacii dostupny tol'ko v chital'nom zale i ne rezerviruyutsya na dom.");
        }
        if (p.isAvailable()) {
            throw new ValidationException("Izdanie svobodno pryamo sejchas — oformite obychnuyu vydachu vmesto rezerva.");
        }
        p.enqueueReader(r.getReaderId());
        Logger.info("Chitatel' " + r.getReaderId() + " dobavlen v ochered' rezerva na izdanie " + p.getInventoryCode());
    }

    public Publication[] searchPublications(String query) {
        String q = query.trim().toLowerCase();
        int matches = 0;
        for (int i = 0; i < publications.size(); i++) {
            Publication p = (Publication) publications.get(i);
            if (p.getTitle().toLowerCase().contains(q) || p.getAuthor().toLowerCase().contains(q)) {
                matches++;
            }
        }
        Publication[] result = new Publication[matches];
        int idx = 0;
        for (int i = 0; i < publications.size(); i++) {
            Publication p = (Publication) publications.get(i);
            if (p.getTitle().toLowerCase().contains(q) || p.getAuthor().toLowerCase().contains(q)) {
                result[idx++] = p;
            }
        }
        return result;
    }

    public void sortByYear() {
        publications.sort(new EntityComparator() {
            @Override
            public int compare(Object first, Object second) {
                Publication p1 = (Publication) first;
                Publication p2 = (Publication) second;
                return Integer.compare(p1.getYear(), p2.getYear());
            }
        });
        Logger.info("Vypolnena sortirovka kataloga po godu izdaniya.");
    }

    public void sortByTitle() {
        publications.sort(new EntityComparator() {
            @Override
            public int compare(Object first, Object second) {
                Publication p1 = (Publication) first;
                Publication p2 = (Publication) second;
                return p1.getTitle().compareToIgnoreCase(p2.getTitle());
            }
        });
        Logger.info("Vypolnena sortirovka kataloga po nazvaniyu.");
    }
    public void printDebtorsReport(LocalDate currentDate) {
        System.out.println("\n==================== OTCHET №1: DOLZHNIKI I SHTRAFY ====================");
        System.out.printf("%-8s | %-26s | %-8s | %-24s | %-10s | %-10s%n",
                "Vydacha", "Chitatel'", "Kod", "Izdanie", "Prosrochka", "Shtraf (rub)");
        System.out.println("---------------------------------------------------------------------------------------------------");
        int debtorCount = 0;
        double totalFines = 0.0;
        for (int i = 0; i < loans.size(); i++) {
            Loan l = (Loan) loans.get(i);
            if (!l.isReturned()) {
                int overdueDays = fineCalculator.calculateOverdueDays(l, currentDate);
                if (overdueDays > 0) {
                    l.setState(LoanState.OVERDUE);
                    Publication pub = findPublicationByCode(l.getPublicationCode());
                    Reader reader = findReaderById(l.getReaderId());
                    double fine = fineCalculator.computeLoanFine(l, pub, currentDate);
                    totalFines += fine;
                    debtorCount++;
                    System.out.printf("%-8s | %-26s | %-8s | %-24s | %6d dn. | %10.2f%n",
                            l.getLoanId(),
                            reader != null ? reader.getFullName() : l.getReaderId(),
                            l.getPublicationCode(),
                            pub != null ? pub.getTitle() : "—",
                            overdueDays,
                            fine);
                }
            }
        }
        if (debtorCount == 0) {
            System.out.println("Zadolzhennostej po srokam vozvrata na datu " + currentDate + " ne obnaruzheno.");
        } else {
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.printf("Itogo prosrochennyh vydach: %d | Summarnyj shtraf k vzyskaniyu: %.2f rub.%n", debtorCount, totalFines);
        }
    }
    public void printTopFivePopularReport() {
        int n = publications.size();
        Publication[] copy = new Publication[n];
        for (int i = 0; i < n; i++) {
            copy[i] = (Publication) publications.get(i);
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (copy[j].getTotalBorrows() < copy[j + 1].getTotalBorrows()) {
                    Publication tmp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = tmp;
                }
            }
        }
        System.out.println("\n==================== OTCHET №2: TOP-5 POPULYARNYH IZDANIJ ====================");
        System.out.printf("%-5s | %-8s | %-28s | %-20s | %-10s%n",
                "Mesto", "Kod", "Nazvanie", "Kategoriya", "Vydach");
        System.out.println("----------------------------------------------------------------------------------");
        int limit = Math.min(5, n);
        for (int i = 0; i < limit; i++) {
            Publication p = copy[i];
            System.out.printf("  #%d  | %-8s | %-28s | %-20s | %6d%n",
                    i + 1, p.getInventoryCode(), p.getTitle(), p.getCategory().getTitle(), p.getTotalBorrows());
        }
    }
    public void printCategorySummaryReport() {
        int books = 0, mags = 0, diss = 0;
        int availBooks = 0, availMags = 0, availDiss = 0;
        for (int i = 0; i < publications.size(); i++) {
            Publication p = (Publication) publications.get(i);
            switch (p.getCategory()) {
                case BOOK:
                    books++;
                    if (p.isAvailable()) availBooks++;
                    break;
                case MAGAZINE:
                    mags++;
                    if (p.isAvailable()) availMags++;
                    break;
                case DISSERTATION:
                    diss++;
                    if (p.isAvailable()) availDiss++;
                    break;
            }
        }
        System.out.println("\n================ OTCHET №3: STRUKTURA BIBLIOTECHNOGO FONDA ================");
        System.out.printf("%-24s | %-12s | %-12s | %-12s%n", "Kategoriya", "Vsego ed.", "Dostupno", "Na rukah");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-24s | %12d | %12d | %12d%n", "Knizhnye izdaniya", books, availBooks, books - availBooks);
        System.out.printf("%-24s | %12d | %12d | %12d%n", "Periodicheskie zhurnaly", mags, availMags, mags - availMags);
        System.out.printf("%-24s | %12d | %12d | %12d%n", "Nauchnye dissertacii", diss, availDiss, diss - availDiss);
    }
    public void printReaderHistory(String readerId) {
        Reader r = findReaderById(readerId);
        if (r == null) {
            throw new ValidationException("Chitatel' s biletom " + readerId + " ne najden.");
        }
        System.out.println("\n=== Istoriya vydach chitatelya: " + r.getFullName() + " (" + r.getReaderId() + ") ===");
        int found = 0;
        for (int i = 0; i < loans.size(); i++) {
            Loan l = (Loan) loans.get(i);
            if (l.getReaderId().equalsIgnoreCase(readerId)) {
                System.out.println("  " + l);
                found++;
            }
        }
        if (found == 0) {
            System.out.println("  Zapisej o vydachah dlya dannogo chitatelya poka net.");
        }
    }
}

