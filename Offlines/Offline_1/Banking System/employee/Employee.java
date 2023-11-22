package employee;

import java.lang.Exception;
import account.Account;
import loan.Loan;

public interface Employee {
    public double lookUp(Account[] accounts,String name);

    default String approveLoan(Loan [] loans) throws OperationNotPermittedException{
        throw new OperationNotPermittedException("You don't have permission for this operation");
    }

    default void changeInterestRate(String accountType,double newInterestRate) throws OperationNotPermittedException{
        throw new OperationNotPermittedException("You don't have permission for this operation");
    }

    default void seeInternalFund() throws OperationNotPermittedException{
        throw new OperationNotPermittedException("You don't have permission for this operation");
    }
}

class OperationNotPermittedException extends Exception {
    public OperationNotPermittedException(String message) {
        super(message);
    }
}