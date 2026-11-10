package com.example.pr5.model;

import com.example.pr5.exception.ValidationException;

public class Reader {
    private String readerId;
    private String fullName;
    private String phone;

    public Reader(String readerId, String fullName, String phone) {
        if (readerId == null || readerId.trim().isEmpty()) {
            throw new ValidationException("ID chitatelya ne mozhet byt' pustym.");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new ValidationException("FIO chitatelya ne mozhet byt' pustym.");
        }
        this.readerId = readerId.trim();
        this.fullName = fullName.trim();
        this.phone = (phone == null) ? "" : phone.trim();
    }

    public String getReaderId() {
        return readerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reader other = (Reader) obj;
        return readerId.equals(other.readerId);
    }

    @Override
    public String toString() {
        return String.format("Bilet %-6s | %-28s | Tel.: %s", readerId, fullName, phone);
    }
}
