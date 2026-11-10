package com.example.pr4;



public class Textbook extends Book {
    private String subjectArea;
    private int courseNumber;

    public Textbook(int id, String title, String author, int publicationYear, String subjectArea, int courseNumber) {
        super(id, title, author, publicationYear);
        this.subjectArea = subjectArea;
        this.courseNumber = courseNumber;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    @Override
    public String getEditionDetails() {
        return "Uchebnik: " + subjectArea + " (" + courseNumber + " kurs), srok vydachi do 90 dnej";
    }
}
