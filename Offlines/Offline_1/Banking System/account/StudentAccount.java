package account;

import loan.Loan;

public class StudentAccount extends Account {
    private static double balanceInterestRate;
    private static double maxWithdrawalAmount;
    private static double maxLoanAmount;

    public static void setBalanceInterestRate(double balanceInterestRate)throws IllegalArgumentException{
        if(balanceInterestRate < 0)throw new IllegalArgumentException("Negative interest rate not allowed");
        StudentAccount.balanceInterestRate = balanceInterestRate;
    }

    public double getBalanceInterestRate() { return balanceInterestRate; }

    public StudentAccount(String name, double balance)throws IllegalArgumentException{
        if(balance < 0)throw new IllegalArgumentException("Balance cannot be negative");
        this.name = name;
        this.balance = balance;
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
        if(amount > maxWithdrawalAmount || balance  < amount) {
            return false;
        }
        else{
            balance -= amount;
            return true;
        }
    }

    @Override
    public Loan requestLoan(double amount) throws IllegalArgumentException{
        if(amount < 0)throw new IllegalArgumentException("Loan amount cannot be negative");
        if(amount > maxLoanAmount)throw new IllegalArgumentException("Loan limit exceeded");
        else{
            return new Loan(name, amount);
        }
    }

    @Override
    public void incrementBalanceByInterest() {
        balance *= (1 + balanceInterestRate/100.0);    
    }
    
}
