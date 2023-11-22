package account;

import loan.Loan;

public class SavingsAccount extends Account {
    private static double balanceInterestRate = 10;
    private static double serviceCharge = 500;

    private static final double MAX_LOAN_AMOUNT = 10000;
    private static final double MIN_BALANCE_ON_WITHDRAWAL = 1000;

    public static void setBalanceInterestRate(double balanceInterestRate)throws IllegalArgumentException{
        if(balanceInterestRate < 0)throw new IllegalArgumentException("Negative interest rate not allowed");
        SavingsAccount.balanceInterestRate = balanceInterestRate;
    }

    public SavingsAccount(String name, double balance)throws IllegalArgumentException {
        if(balance < 0)throw new IllegalArgumentException("Balance cannot be negative");
        this.name = name;
        this.balance = balance;
        setType();
    }

    @Override
    public void setType(){
        type = "Savings";
    }

    @Override
    public boolean deposit(double amount)throws IllegalArgumentException{
        if(amount < 0)throw new IllegalArgumentException("Deposit amount cannot be negative");
        balance += amount;
        return true;
    }

    @Override
    public boolean withdraw(double amount)throws IllegalArgumentException{
        if(amount < 0)throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        if (balance - amount < MIN_BALANCE_ON_WITHDRAWAL)return false;
        else {
            balance -= amount;
            return true;
        }
    }

    @Override
    public Loan requestLoan(double amount)throws IllegalArgumentException{
        if(amount < 0)throw new IllegalArgumentException("Loan amount cannot be negative");
        if(amount > MAX_LOAN_AMOUNT)throw new IllegalArgumentException("Loan limit exceeded");
        else{
            return new Loan(name, amount);
        }
    }

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
