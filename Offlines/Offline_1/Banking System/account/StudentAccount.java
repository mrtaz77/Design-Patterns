package account;

import loan.Loan;

public class StudentAccount extends Account {
    private static double balanceInterestRate = 5;

    private static final double MAX_WITHDRAWAL_AMOUNT = 10000;
    private static final double MAX_LOAN_AMOUNT = 1000;

    public static void setBalanceInterestRate(double balanceInterestRate) {
        StudentAccount.balanceInterestRate = balanceInterestRate;
    }

    public StudentAccount(String name, double balance){
        this.name = name;
        this.balance = balance;
        setType();
    }

    @Override
    public void setType(){
        type = "Student";
    }

    @Override
    public boolean deposit(double amount) {
        balance += amount;
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        if(amount > MAX_WITHDRAWAL_AMOUNT || balance  < amount) {
            return false;
        }
        else{
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
    }

    @Override
    public void incrementBalanceByInterest() {
        balance *= (1 + balanceInterestRate/100.0);    
    }
    
}
