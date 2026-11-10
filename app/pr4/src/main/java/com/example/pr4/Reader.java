package com.example.pr4;


public class Reader {
    private int readerId;
    private String fullName;
    private String phone;

    public Reader(int readerId, String fullName, String phone) {
        this.readerId = readerId;
        this.fullName = fullName;
        this.phone = phone;
    }

    public int getReaderId() {
        return readerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reader other = (Reader) obj;
        return this.readerId == other.readerId;
    }

    @Override
    public String toString() {
        return String.format("Chitatel'skij bilet #%d | FIO: %s | Tel.: %s", readerId, fullName, phone);
    }
}
