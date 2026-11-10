package com.example.pr5.model;

public class Magazine extends Publication {
    private int issueNumber;
    private String monthName;

    public Magazine(String inventoryCode, String title, String author, int year, int issueNumber, String monthName) {
        super(inventoryCode, title, author, year);
        this.issueNumber = Math.max(1, issueNumber);
        this.monthName = (monthName == null || monthName.trim().isEmpty()) ? "Yanvar'" : monthName.trim();
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public String getMonthName() {
        return monthName;
    }

    @Override
    public PublicationCategory getCategory() {
        return PublicationCategory.MAGAZINE;
    }

    @Override
    public double calcFine(int overdueDays) {
        if (overdueDays <= 0) return 0.0;

        double base = overdueDays * getCategory().getBaseDailyRate();
        return overdueDays > 5 ? base * 1.5 : base;
    }

    @Override
    public String getSpecificMetadata() {
        return "Vypusk №" + issueNumber + " (" + monthName + ")";
    }
}
