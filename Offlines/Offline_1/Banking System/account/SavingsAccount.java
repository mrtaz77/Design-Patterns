package account;

import loan.Loan;

public class SavingsAccount extends Account {
    private static double balanceInterestRate = 10;
    private static double serviceCharge = 500;

    private static final double MAX_LOAN_AMOUNT = 10000;
    private static final double MIN_BALANCE_ON_WITHDRAWAL = 1000;

    public static void setBalanceInterestRate(double balanceInterestRate) {
        SavingsAccount.balanceInterestRate = balanceInterestRate;
    }

    public SavingsAccount(String name, double balance){
        this.name = name;
        this.balance = balance;
        setType();
    }

    @Override
    public void setType(){
        type = "Savings";
    }

    @Override
    public boolean deposit(double amount) {
        balance += amount;
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount < MIN_BALANCE_ON_WITHDRAWAL)return false;
        else {
            balance -= amount;
            return true;
        }
    }

    @Override
    public Loan requestLoan(double amount) {
        if(amount > MAX_LOAN_AMOUNT)return null;
        else{
            return new Loan(name, amount);
        }
    }

    @Override
    public double getBalanceInterestRate() { return balanceInterestRate; }

    @Override
    public void deductServiceCharge() {
        balance -= serviceCharge;    
    }

    @Override
    public void incrementBalanceByInterest() {
        balance *= (1 + balanceInterestRate/100.0);    
    }
}
