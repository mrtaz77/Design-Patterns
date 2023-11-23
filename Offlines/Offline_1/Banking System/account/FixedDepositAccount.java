package account;

import loan.Loan;

public class FixedDepositAccount extends Account {
    private static double balanceInterestRate;
    private static double serviceCharge;
    private static double minInitBalance; 
    private static double minDeposit;
    private static int minWithdrawalAge;
    private static double maxLoanAmount;

    private int accountAge;

    public static void setBalanceInterestRate(double balanceInterestRate)throws IllegalArgumentException {
        if(balanceInterestRate < 0)throw new IllegalArgumentException("Negative interest rate not allowed");
        FixedDepositAccount.balanceInterestRate = balanceInterestRate;
    }
    
    public double getBalanceInterestRate() { return balanceInterestRate; }

    public static double getServiceCharge() {
        return serviceCharge;
    }

    public static void setServiceCharge(double serviceCharge) {
        FixedDepositAccount.serviceCharge = serviceCharge;
    }

    public static double getMinInitBalance() {
        return minInitBalance;
    }

    public static void setMinInitBalance(double minInitBalance) {
        FixedDepositAccount.minInitBalance = minInitBalance;
    }

    public static double getMinDeposit() {
        return minDeposit;
    }

    public static void setMinDeposit(double minDeposit) {
        FixedDepositAccount.minDeposit = minDeposit;
    }

    public static int getMinWithdrawalAge() {
        return minWithdrawalAge;
    }

    public static void setMinWithdrawalAge(int minWithdrawalAge) {
        FixedDepositAccount.minWithdrawalAge = minWithdrawalAge;
    }

    public static double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public static void setMaxLoanAmount(double maxLoanAmount) {
        FixedDepositAccount.maxLoanAmount = maxLoanAmount;
    }
    
    
    public FixedDepositAccount(String name, double balance)throws IllegalArgumentException {
        if(balance < minInitBalance)throw new IllegalArgumentException("Balance too low");
        this.name = name;
        this.balance = balance;
        accountAge = 0;
    }

    public void setAccountAge(int accountAge) { this.accountAge = accountAge; }
    public int getAccountAge() { return accountAge; }

    @Override
    public void deposit(double amount)throws IllegalArgumentException {
        if(amount < minDeposit)throw new IllegalArgumentException("Deposit too low");
        balance += amount;
    }

    @Override
    public void withdraw(double amount)throws IllegalArgumentException {
        if(amount < 0)throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        if(accountAge < minWithdrawalAge)throw new IllegalArgumentException("Withdrawal before maturity age is denied");
        if(balance < amount)throw new IllegalArgumentException("Withdrawal amount more than balance");
        balance -= amount;
    }

    @Override
    public Loan requestLoan(double amount,double loanInterestRate)throws IllegalArgumentException {
        if(amount < 0)throw new IllegalArgumentException("Loan amount cannot be negative");
        if(amount > maxLoanAmount)throw new IllegalArgumentException("Loan limit exceeded");
        return new Loan(name, amount, loanInterestRate);
    }

    public void deductServiceCharge() {
        balance -= serviceCharge;
    }

    @Override
    public void incrementBalanceByInterest() {    
        balance *= (1 + balanceInterestRate/100.0);    
    }
}
