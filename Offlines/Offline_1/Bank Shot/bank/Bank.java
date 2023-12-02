package bank;

public class Bank {
    private double internalFund;
    private double loanInterestRate;
    private int year;
    
    public Bank(double internalFund,double loanInterestRate) {
        this.internalFund = internalFund;
        this.loanInterestRate = loanInterestRate;
        year = 0;
    }

    public double getLoanInterestRate() { return loanInterestRate; }
    public void setLoanInterestRate(double loanInterestRate) { this.loanInterestRate = loanInterestRate; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getInternalFund() { return internalFund; }
    public void setInternalFund(double internalFund) { this.internalFund = internalFund; }
}