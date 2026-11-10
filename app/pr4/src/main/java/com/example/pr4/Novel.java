package com.example.pr4;



public class Novel extends Book {
    private String literaryGenre;

    public Novel(int id, String title, String author, int publicationYear, String literaryGenre) {
        super(id, title, author, publicationYear);
        this.literaryGenre = literaryGenre;
    }

    public String getLiteraryGenre() {
        return literaryGenre;
    }

    public void setLiteraryGenre(String literaryGenre) {
        this.literaryGenre = literaryGenre;
    }

    @Override
    public String getEditionDetails() {
        return "Roman: zhanr \"" + literaryGenre + "\", srok vydachi do 14 dnej";
    }
}
