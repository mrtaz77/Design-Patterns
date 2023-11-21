package account;

import loan.Loan;

public abstract class Account {
    private static final double LOAN_INTEREST_RATE = 10;

    protected String name;
    protected double balance;
    protected String type;

    public final String getName() { return name; }
    public final void setName(String name) { this.name = name; }

    public final double queryDeposit(){ return balance; };
    public final void setBalance(double balance) { this.balance = balance; }
    
    public final String getType(){ return type; }

    public void deductLoanInterest(){
        balance *= (1 - LOAN_INTEREST_RATE/100.0);
    }

    public abstract void setType();
    public abstract boolean deposit(double amount);
    public abstract boolean withdraw(double amount);
    public abstract Loan requestLoan(double amount);
    public abstract double getBalanceInterestRate();
    public abstract void deductServiceCharge();
    public abstract void incrementBalanceByInterest();
}
