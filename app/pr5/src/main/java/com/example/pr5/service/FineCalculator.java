package com.example.pr5.service;

import com.example.pr5.model.FinePolicy;
import com.example.pr5.model.Loan;
import com.example.pr5.model.Publication;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class FineCalculator implements FinePolicy {
    private final double defaultRatePerDay;

    public FineCalculator(double defaultRatePerDay) {
        this.defaultRatePerDay = defaultRatePerDay;
    }

    @Override
    public double calcFine(int overdueDays) {
        return overdueDays > 0 ? overdueDays * defaultRatePerDay : 0.0;
    }

    public int calculateOverdueDays(Loan loan, LocalDate referenceDate) {
        LocalDate effectiveEnd = loan.isReturned() ? loan.getReturnDate() : referenceDate;
        if (effectiveEnd.isAfter(loan.getDueDate())) {
            return (int) ChronoUnit.DAYS.between(loan.getDueDate(), effectiveEnd);
        }
        return 0;
    }


    public double computeLoanFine(Loan loan, Publication publication, LocalDate referenceDate) {
        int overdue = calculateOverdueDays(loan, referenceDate);
        if (overdue <= 0 || publication == null) {
            return 0.0;
        }
        return publication.calcFine(overdue);
    }
}

