package model;

import java.time.LocalDate;
import java.time.LocalTime;

// Booking model class with encapsulated fields
public class Booking {
    private String customerName;
    private String phone;
    private int tableSize;
    private LocalDate date;
    private LocalTime time;

    public Booking() {}

    public Booking(String customerName, String phone, int tableSize, LocalDate date, LocalTime time) {
        this.customerName = customerName;
        this.phone = phone;
        this.tableSize = tableSize;
        this.date = date;
        this.time = time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
