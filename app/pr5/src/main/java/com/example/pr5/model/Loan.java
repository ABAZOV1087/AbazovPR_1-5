package com.example.pr5.model;

import java.time.LocalDate;

public class Loan {
    private String loanId;
    private String readerId;
    private String publicationCode;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int extensionCount;
    private LoanState state;

    public Loan(String loanId, String readerId, String publicationCode, LocalDate issueDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.readerId = readerId;
        this.publicationCode = publicationCode;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.extensionCount = 0;
        this.state = LoanState.ACTIVE;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getReaderId() {
        return readerId;
    }

    public String getPublicationCode() {
        return publicationCode;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getExtensionCount() {
        return extensionCount;
    }

    public void incrementExtensions() {
        this.extensionCount++;
        this.state = LoanState.EXTENDED;
    }

    public LoanState getState() {
        return state;
    }

    public void setState(LoanState state) {
        this.state = state;
    }

    public boolean isReturned() {
        return returnDate != null || state == LoanState.RETURNED;
    }

    @Override
    public String toString() {
        return String.format("Vydacha %-6s | Chitatel': %-6s | Izdanie: %-6s | Vydano: %s | Srok do: %s | Vozvrat: %-10s | Prodelenij: %d | Status: %s",
                loanId, readerId, publicationCode, issueDate, dueDate,
                returnDate == null ? "—" : returnDate.toString(),
                extensionCount, state.getLabel());
    }
}
