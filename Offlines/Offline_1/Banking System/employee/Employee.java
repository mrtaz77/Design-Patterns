package employee;

import java.lang.Exception;
import java.util.ArrayList;
import bank.Bank;
import account.Account;
import loan.Loan;

public interface Employee {
    public double lookUp(Account account);

    default void approveLoan(ArrayList<Loan> loans) throws OperationNotPermittedException{
        throw new OperationNotPermittedException("You don't have permission for this operation");
    }

    default void changeInterestRate(String accountType,double newInterestRate) throws OperationNotPermittedException{
        throw new OperationNotPermittedException("You don't have permission for this operation");
    }

    default double seeInternalFund(Bank bank) throws OperationNotPermittedException{
        throw new OperationNotPermittedException("You don't have permission for this operation");
    }
}

class OperationNotPermittedException extends Exception {
    public OperationNotPermittedException(String message) {
        super(message);
    }
}