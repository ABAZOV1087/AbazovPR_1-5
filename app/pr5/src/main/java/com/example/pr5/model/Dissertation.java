package com.example.pr5.model;

public class Dissertation extends Publication {
    private String academicField;
    private String degreeLevel;

    public Dissertation(String inventoryCode, String title, String author, int year, String academicField, String degreeLevel) {
        super(inventoryCode, title, author, year);
        this.academicField = academicField;
        this.degreeLevel = degreeLevel;
    }

    public String getAcademicField() {
        return academicField;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    @Override
    public PublicationCategory getCategory() {
        return PublicationCategory.DISSERTATION;
    }

    @Override
    public double calcFine(int overdueDays) {
        if (overdueDays <= 0) return 0.0;
        return overdueDays * getCategory().getBaseDailyRate() + 100.0;
    }

    @Override
    public String getSpecificMetadata() {
        return "Спец.: " + academicField + " (" + degreeLevel + ") [ТОЛЬКО ЧИТАЛЬНЫЙ ЗАЛ]";
    }
}

