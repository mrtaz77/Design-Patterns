package account;

import loan.Loan;

public class StudentAccount extends Account {
    private static double balanceInterestRate;
    private static double maxWithdrawalAmount;
    private static double maxLoanAmount;

    public static void setBalanceInterestRate(double balanceInterestRate)throws IllegalArgumentException {
        if(balanceInterestRate < 0)throw new IllegalArgumentException("Negative interest rate not allowed");
        StudentAccount.balanceInterestRate = balanceInterestRate;
    }

    public double getBalanceInterestRate() { return balanceInterestRate; }

    public static double getMaxWithdrawalAmount() {
        return maxWithdrawalAmount;
    }

    public static void setMaxWithdrawalAmount(double maxWithdrawalAmount) {
        StudentAccount.maxWithdrawalAmount = maxWithdrawalAmount;
    }

    public static double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public static void setMaxLoanAmount(double maxLoanAmount) {
        StudentAccount.maxLoanAmount = maxLoanAmount;
    }

    public StudentAccount(String name, double balance)throws IllegalArgumentException {
        if(balance < 0)throw new IllegalArgumentException("Balance cannot be negative");
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void deposit(double amount)throws IllegalArgumentException {
        if(amount < 0)throw new IllegalArgumentException("Deposit amount cannot be negative");
        balance += amount;
    }

    @Override
    public void withdraw(double amount)throws IllegalArgumentException {
        if(amount < 0)throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        if(amount > maxWithdrawalAmount)throw new IllegalArgumentException("Withdrawal amount more than withdrawal limit");
        if( balance  < amount)throw new IllegalArgumentException("Withdrawal amount more than balance");
        balance -= amount;
    }

    @Override
    public Loan requestLoan(double amount, double loanInterestRate) throws IllegalArgumentException {
        if(amount < 0)throw new IllegalArgumentException("Loan amount cannot be negative");
        if(amount > maxLoanAmount)throw new IllegalArgumentException("Loan limit exceeded");
        return new Loan(name, amount, loanInterestRate);
    }

    @Override
    public void incrementBalanceByInterest() {
        balance *= (1 + balanceInterestRate/100.0);    
    }
    
}
