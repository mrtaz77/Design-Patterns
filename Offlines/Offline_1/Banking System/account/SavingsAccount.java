package account;

import loan.Loan;

public class SavingsAccount extends Account {
    private static double balanceInterestRate;
    private static double serviceCharge;
    private static double maxLoanAmount;
    private static double minBalanceOnWithdrawal;

    public static void setBalanceInterestRate(double balanceInterestRate)throws IllegalArgumentException{
        if(balanceInterestRate < 0)throw new IllegalArgumentException("Negative interest rate not allowed");
        SavingsAccount.balanceInterestRate = balanceInterestRate;
    }

    public double getBalanceInterestRate() { return balanceInterestRate; }

    public static double getServiceCharge() {
        return serviceCharge;
    }

    public static void setServiceCharge(double serviceCharge) {
        SavingsAccount.serviceCharge = serviceCharge;
    }

    public static double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public static void setMaxLoanAmount(double maxLoanAmount) {
        SavingsAccount.maxLoanAmount = maxLoanAmount;
    }

    public static double getMinBalanceOnWithdrawal() {
        return minBalanceOnWithdrawal;
    }

    public static void setMinBalanceOnWithdrawal(double minBalanceOnWithdrawal) {
        SavingsAccount.minBalanceOnWithdrawal = minBalanceOnWithdrawal;
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
        if (balance - amount < minBalanceOnWithdrawal)return false;
        else {
            balance -= amount;
            return true;
        }
    }

    @Override
    public Loan requestLoan(double amount)throws IllegalArgumentException{
        if(amount < 0)throw new IllegalArgumentException("Loan amount cannot be negative");
        if(amount > maxLoanAmount)throw new IllegalArgumentException("Loan limit exceeded");
        else{
            return new Loan(name, amount);
        }
    }

    public void deductServiceCharge() {
        balance -= serviceCharge;    
    }

    @Override
    public void incrementBalanceByInterest() {
        balance *= (1 + balanceInterestRate/100.0);    
    }
}
