package bank;

import java.util.*;

import account.*;
import employee.*;
import loan.*;

public class BankManagement {
    private HashMap<String,Account> accounts;
    private HashMap<String,Employee> employees;
    private ArrayList<Loan> loans;
    private Bank bank;

    private void applyInterestToAccounts() {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            double oldAccountBalance = account.queryDeposit();
            account.incrementBalanceByInterest();
            double newAccountBalance = account.queryDeposit();

            bank.setInternalFund(
                bank.getInternalFund() - (newAccountBalance - oldAccountBalance)
            );
        }
    }

    private void deductLoanInterestFromAccounts() {
        for (Loan loan: loans){
            if(loan.getStatus() == Status.PENDING)continue;
            var account = accounts.get(loan.getApplicantName());
            
            account.setBalance(
                account.queryDeposit() - loan.getLoanInterest()
            );

            bank.setInternalFund(
                bank.getInternalFund() + loan.getLoanInterest()
            );
        }
    }

    private void deductServiceChargeFromAccounts() {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            if (account instanceof FixedDepositAccount) {
                ((FixedDepositAccount) account).deductServiceCharge();

                bank.setInternalFund(
                    bank.getInternalFund() + FixedDepositAccount.getServiceCharge()
                );

            } else if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).deductServiceCharge();

                bank.setInternalFund(
                    bank.getInternalFund() + SavingsAccount.getServiceCharge()
                );
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
        double fixedDepositBalanceInterestRate, 
        double fixedDepositServiceCharge,
        double fixedDepositMinInitBalance, 
        double fixedDepositMinDeposit,
        int fixedDepositMinWithdrawalAge, 
        double fixedDepositMaxLoanAmount,

        // Parameters for SavingsAccount class
        double savingsBalanceInterestRate, 
        double savingsServiceCharge,
        double savingsMinBalanceOnWithdrawal, 
        double savingsMaxLoanAmount,

        // Parameters for StudentAccount class
        double studentBalanceInterestRate, 
        double studentMaxWithdrawalAmount, 
        double studentMaxLoanAmount
    ) {
        BankInitializer.initializeFixedDepositAccount(
            fixedDepositBalanceInterestRate,
            fixedDepositServiceCharge, 
            fixedDepositMinInitBalance,
            fixedDepositMinDeposit, 
            fixedDepositMinWithdrawalAge, 
            fixedDepositMaxLoanAmount
        );

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

    public BankManagement(double internalFund,double loanInterestRate){
        bank = new Bank(internalFund,loanInterestRate);
        accounts = new HashMap<String,Account>();
        employees = new HashMap<String,Employee>();
        loans = new ArrayList<Loan>();
    }

    public Account getAccountByName(String name){
        return this.accounts.get(name);
    }

    public Employee getEmployeeByName(String name){
        return this.employees.get(name);
    }

    private boolean canCreateAccount(String name, double initBalance, String type){
        if(type == "Fixed Deposit" && initBalance < FixedDepositAccount.getMinInitBalance())return false;
        if(initBalance <= 0)return false;
        return getAccountByName(name) == null;
    }

    public void createAccount(String name, double initBalance, String type)throws IllegalArgumentException{
        if(canCreateAccount(name, initBalance, type)){
            switch(type){
                case "Fixed Deposit" -> accounts.put(name, new FixedDepositAccount(name, initBalance));
                case "Student" -> accounts.put(name, new StudentAccount(name, initBalance));
                case "Savings" -> accounts.put(name, new SavingsAccount(name, initBalance));
                default -> throw new IllegalArgumentException("Invalid Account Type");
            }
        }
        else {
            throw new IllegalArgumentException("Account cannot be created");
        }
    }

    public void createEmployee(String name,String type)throws IllegalArgumentException{
        Employee employee = getEmployeeByName(name);
        if(employee != null) throw new IllegalArgumentException("Employee already exists");

        switch (type) {
            case "Officer" -> employees.put(name, new Officer(name));
            case "Cashier" -> employees.put(name, new Cashier(name));
            case "Managing Director" -> employees.put(name, new ManagingDirector(name));
            default -> throw new IllegalArgumentException("Invalid Employee Type");
        }
    }

    public String getPendingLoanUserNames(){
        var pendingLoanUsers = new HashSet<String>();

        for(Loan loan : loans)if(loan.getStatus() == Status.PENDING)pendingLoanUsers.add(loan.getApplicantName());
        
        var pendingLoanUserNames = "";

        Iterator<String> itr = pendingLoanUsers.iterator();
        while (itr.hasNext()) {
            pendingLoanUserNames += itr.next();
            if(itr.hasNext())pendingLoanUserNames += ", ";
        }

        return pendingLoanUserNames;
    }

    public void inc(){
        bank.setYear(bank.getYear() + 1);
        applyInterestToAccounts();
        deductLoanInterestFromAccounts();
        deductServiceChargeFromAccounts();
        incrementAgeOfAccounts();
    }

    public void deposit(String name, double amount)throws IllegalArgumentException{
        Account account = getAccountByName(name);
        if(account == null)throw new IllegalArgumentException("Account not found");
        account.deposit(amount);
    }

    public void withdraw(String name, double amount)throws IllegalArgumentException{
        Account account = getAccountByName(name);
        if(account == null)throw new IllegalArgumentException("Account not found");
        account.withdraw(amount);
    }

    public double queryDepositByName(String name)throws IllegalArgumentException{
        Account account = getAccountByName(name);
        if(account == null)throw new IllegalArgumentException("Account not found");
        return account.queryDeposit();
    }

    public double queryApprovedLoansByName(String name)throws IllegalArgumentException{
        Account account = getAccountByName(name);
        if(account == null)throw new IllegalArgumentException("Account not found");
        double loanAmount = 0;
        for(Loan loan : loans){
            if(loan.getApplicantName().equals(name) && loan.getStatus() == Status.APPROVED){
                loanAmount += loan.getAmount();
            }
        }
        return loanAmount;
    }

    public String lookUp(String employeeName,String accountName){
        Employee employee = getEmployeeByName(employeeName);
        if(employee == null)return "Employee not found";
        
        Account account = getAccountByName(accountName);
        if(account == null)return "Account not found";

        double balance = employee.lookUp(account);
        return accountName + " current balance: $" + balance;
    }

    public String change(String employeeName,String accountType,double newInterestRate){
        Employee employee = getEmployeeByName(employeeName);
        if(employee == null)return "Employee not found";
        
        if(employee instanceof ManagingDirector){
            ((ManagingDirector) employee).changeInterestRate(accountType,newInterestRate);
            return accountType+" interest rate changed to " + newInterestRate;
        }
        else{
            return "You don't have permission for this operation";
        }
    }

    public String approveLoan(String employeeName){
        Employee employee = getEmployeeByName(employeeName);
        if(employee == null)return "Employee not found";

        var pendingLoanUserNames = getPendingLoanUserNames();

        if(pendingLoanUserNames.length() == 0)return "No pending loans";
        
        if(employee instanceof ManagingDirector){
            ((ManagingDirector) employee).approveLoan(loans);
        }
        else if(employee instanceof Officer){
            ((Officer) employee).approveLoan(loans);
        }
        else{
            return "You don't have permission for this operation";
        }

        return "Loans for " + pendingLoanUserNames + " approved";
    }

    public String requestLoan(String name,double amount)throws IllegalArgumentException{
        Account account = getAccountByName(name);
        if(account == null)throw new IllegalArgumentException("Account not found");
        loans.add(account.requestLoan(amount, bank.getLoanInterestRate()));
        return "Loan request successful, sent for approval";
    }
}