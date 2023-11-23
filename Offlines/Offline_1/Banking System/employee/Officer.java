package employee;

import java.util.ArrayList;

import account.Account;
import loan.*;

public class Officer implements Employee{
    private String name;

    public Officer(String name) {
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

    public void approveLoan(ArrayList<Loan> loans){
        for (Loan loan : loans){
            loan.setApproverName(name);
            loan.setStatus(Status.APPROVED);
        }
    }
}
