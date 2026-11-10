package com.example.pr5.model;

public enum PublicationCategory {
    BOOK("Knizhnoe izdanie", 14, 15.0, true),
    MAGAZINE("Periodicheskij zhurnal", 7, 10.0, true),
    DISSERTATION("Nauchnaya dissertaciya", 0, 50.0, false);

    private final String title;
    private final int standardLoanDays;
    private final double baseDailyRate;
    private final boolean takeHomeAllowed;

    PublicationCategory(String title, int standardLoanDays, double baseDailyRate, boolean takeHomeAllowed) {
        this.title = title;
        this.standardLoanDays = standardLoanDays;
        this.baseDailyRate = baseDailyRate;
        this.takeHomeAllowed = takeHomeAllowed;
    }

    public String getTitle() {
        return title;
    }

    public int getStandardLoanDays() {
        return standardLoanDays;
    }

    public double getBaseDailyRate() {
        return baseDailyRate;
    }

    public boolean isTakeHomeAllowed() {
        return takeHomeAllowed;
    }
}
