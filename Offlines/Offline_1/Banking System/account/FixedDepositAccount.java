package account;

import loan.Loan;

public class FixedDepositAccount extends Account {
    private static double balanceInterestRate = 5;
    private static double serviceCharge = 500;

    public static final double MIN_INIT_BALANCE = 100000; 
    private static final double MIN_DEPOSIT = 50000;
    private static final int MIN_WITHDRAWAL_AGE = 1;
    private static final double MAX_LOAN_AMOUNT = 100000;

    private int accountAge;

    public static void setBalanceInterestRate(double balanceInterestRate)throws IllegalArgumentException{
        if(balanceInterestRate < 0)throw new IllegalArgumentException("Negative interest rate not allowed");
        FixedDepositAccount.balanceInterestRate = balanceInterestRate;
    }

    public FixedDepositAccount(String name, double balance)throws IllegalArgumentException {
        if(balance < MIN_INIT_BALANCE)throw new IllegalArgumentException("Balance too low");
        this.name = name;
        this.balance = balance;
        accountAge = 0;
        setType();
    }

    public void setAccountAge(int accountAge) { this.accountAge = accountAge; }
    public int getAccountAge() { return accountAge; }

    @Override
    public void setType() {
        type = "Fixed deposit";    
    }

    @Override
    public boolean deposit(double amount)throws IllegalArgumentException{
        if(amount < MIN_DEPOSIT)throw new IllegalArgumentException("Deposit too low");
        balance += amount;
        return true;
    }

    @Override
    public boolean withdraw(double amount)throws IllegalArgumentException{
        if(amount < 0)throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        if(accountAge < MIN_WITHDRAWAL_AGE)return false;
        balance -= amount;
        return true;
    }

    @Override
    public Loan requestLoan(double amount)throws IllegalArgumentException{
        if(amount < 0)throw new IllegalArgumentException("Loan amount cannot be negative");
        if(amount > MAX_LOAN_AMOUNT)throw new IllegalArgumentException("Loan limit exceeded");
        else{
            return new Loan(name, amount);
        }}

    @Override
    public double getBalanceInterestRate() { return balanceInterestRate; }

    public void deductServiceCharge() {
        balance -= serviceCharge;
    }

    @Override
    public void incrementBalanceByInterest() {    
        balance *= (1 + balanceInterestRate/100.0);    
    }
    
}
