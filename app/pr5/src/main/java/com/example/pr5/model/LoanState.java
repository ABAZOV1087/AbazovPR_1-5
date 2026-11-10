package com.example.pr5.model;

public enum LoanState {
    ACTIVE("Na rukah", true),
    EXTENDED("Prodlena", true),
    RETURNED("Vozvraschena", false),
    OVERDUE("Prosrochena", true);

    private final String label;
    private final boolean openLoan;

    LoanState(String label, boolean openLoan) {
        this.label = label;
        this.openLoan = openLoan;
    }

    public String getLabel() {
        return label;
    }

    public boolean isOpenLoan() {
        return openLoan;
    }
}
