package report;

import java.util.Date;

public class MonthYear{
    public int month,year;
    public Date date;
    public MonthYear(int month, int year, Date date) {
        this.month = month;
        this.year = year;
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}