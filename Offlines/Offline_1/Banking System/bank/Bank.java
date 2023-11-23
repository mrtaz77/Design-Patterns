package bank;

public class Bank {
    private double internalFund;
    private int year;
    
    public Bank(double internalFund){
        this.internalFund = internalFund;
        year = 0;
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getInternalFund() { return internalFund; }
    public void setInternalFund(double internalFund) { this.internalFund = internalFund; }
}