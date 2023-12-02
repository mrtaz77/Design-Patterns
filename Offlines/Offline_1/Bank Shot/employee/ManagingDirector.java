package employee;

import account.*;
import bank.Bank;
import loan.*;

public class ManagingDirector implements Employee {

    private String name;

    public ManagingDirector(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double lookUp(Account account) {
        return account.queryDeposit();
    }

    public void changeInterestRate(String accountType,double newInterestRate){
        switch (accountType) {
            case "Savings":
                SavingsAccount.setBalanceInterestRate(newInterestRate);
                break;
            case "Fixed Deposit":
                FixedDepositAccount.setBalanceInterestRate(newInterestRate);
                break;
            case "Student":
                StudentAccount.setBalanceInterestRate(newInterestRate);
            default:
                break;
        }
    }

    public double seeInternalFund(Bank bank){
        return bank.getInternalFund();
    }

    public void approveLoan(Loan loan){
        loan.setApproverName(name);
        loan.setStatus(Status.APPROVED);    
    }
}
