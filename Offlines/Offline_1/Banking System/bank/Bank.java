package bank;

import java.util.*; 
import account.*;
import employee.*;
import loan.Loan;

public class Bank {
    private HashMap<String,Account> accounts;
    private HashMap<String,Employee> employees;
    private ArrayList<Loan> loans;
    private double internalFund;
    private int year;

    private void applyInterestToAccounts() {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            account.incrementBalanceByInterest();
        }
    }

    private void deductLoanInterestFromAccounts() {
        for (Loan loan: loans){
            accounts.get(loan.getApplicantName()).deductLoanInterest(loan);
        }
    }

    private void deductServiceChargeFromAccounts() {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            if (account instanceof FixedDepositAccount) {
                ((FixedDepositAccount) account).deductServiceCharge();
            } else if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).deductServiceCharge();
            }
        }
    }

    private void incrementAgeOfAccounts() {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            if (account instanceof FixedDepositAccount) {
                FixedDepositAccount fixedDepositAccount = (FixedDepositAccount) account;
                int age = fixedDepositAccount.getAccountAge();
                fixedDepositAccount.setAccountAge(age + 1);
            }
        }
    }

    public static void initialize(
        // Parameters for FixedDepositAccount class
        double fixedDepositBalanceInterestRate, double fixedDepositServiceCharge,
        double fixedDepositMinInitBalance, double fixedDepositMinDeposit,
        int fixedDepositMinWithdrawalAge, double fixedDepositMaxLoanAmount,

        // Parameters for Loan class
        double loanInterestRate,

        // Parameters for SavingsAccount class
        double savingsBalanceInterestRate, double savingsServiceCharge,
        double savingsMinBalanceOnWithdrawal, double savingsMaxLoanAmount,

        // Parameters for StudentAccount class
        double studentBalanceInterestRate, double studentMaxWithdrawalAmount, double studentMaxLoanAmount
    ) {
        BankInitializer.initializeFixedDepositAccount(
            fixedDepositBalanceInterestRate,
            fixedDepositServiceCharge, 
            fixedDepositMinInitBalance,
            fixedDepositMinDeposit, 
            fixedDepositMinWithdrawalAge, 
            fixedDepositMaxLoanAmount
        );

        BankInitializer.initializeLoan(loanInterestRate);

        BankInitializer.initializeSavingsAccount(
            savingsBalanceInterestRate, 
            savingsServiceCharge, 
            savingsMinBalanceOnWithdrawal, 
            savingsMaxLoanAmount
        );

        BankInitializer.initializeStudentAccount(
            studentBalanceInterestRate, 
            studentMaxWithdrawalAmount, 
            studentMaxLoanAmount
        );
    }

    public Bank(double internalFund){
        accounts = new HashMap<String,Account>();
        employees = new HashMap<String,Employee>();
        loans = new ArrayList<Loan>();
        this.internalFund = internalFund;
        year = 0;
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getInternalFund() { return internalFund; }
    public void setInternalFund(double internalFund) { this.internalFund = internalFund; }

    public Account searchAccount(String name){
        return this.accounts.get(name);
    }

    public Employee searchEmployee(String name){
        return this.employees.get(name);
    }

    public void inc(){
        year += 1;
        applyInterestToAccounts();
        deductLoanInterestFromAccounts();
        deductServiceChargeFromAccounts();
        incrementAgeOfAccounts();
    }
}

class BankInitializer {
    // static variables of FixedDepositAccount class
    static void initializeFixedDepositAccount(
        double balanceInterestRate, 
        double serviceCharge, 
        double minInitBalance,
        double minDeposit, 
        int minWithdrawalAge, 
        double maxLoanAmount
    ) {
        FixedDepositAccount.setBalanceInterestRate(balanceInterestRate);
        FixedDepositAccount.setServiceCharge(serviceCharge);
        FixedDepositAccount.setMinInitBalance(minInitBalance);
        FixedDepositAccount.setMinDeposit(minDeposit);
        FixedDepositAccount.setMinWithdrawalAge(minWithdrawalAge);
        FixedDepositAccount.setMaxLoanAmount(maxLoanAmount);
    }

    static void initializeLoan(double interestRate) {
        Loan.setLoanInterestRate(interestRate);
    }

    static void initializeSavingsAccount(
        double balanceInterestRate,
        double serviceCharge,
        double minBalanceOnWithdrawal,
        double maxLoanAmount
    ) {
        SavingsAccount.setBalanceInterestRate(balanceInterestRate);
        SavingsAccount.setServiceCharge(serviceCharge);
        SavingsAccount.setMinBalanceOnWithdrawal(minBalanceOnWithdrawal);
        SavingsAccount.setMaxLoanAmount(maxLoanAmount);
    }

    static void initializeStudentAccount(
        double balanceInterestRate, 
        double maxWithdrawalAmount, 
        double maxLoanAmount
    ) {
        StudentAccount.setBalanceInterestRate(balanceInterestRate);
        StudentAccount.setMaxWithdrawalAmount(maxWithdrawalAmount);
        StudentAccount.setMaxLoanAmount(maxLoanAmount);
    }
}