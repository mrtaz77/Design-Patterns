package bank;

import account.*;

class BankInitializer {
    // static variables of FixedDepositAccount class
    static void initFixedDepositAccountVariables(
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

    static void initSavingsAccountVariables(
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

    static void initStudentAccountVariables(
        double balanceInterestRate, 
        double maxWithdrawalAmount, 
        double maxLoanAmount
    ) {
        StudentAccount.setBalanceInterestRate(balanceInterestRate);
        StudentAccount.setMaxWithdrawalAmount(maxWithdrawalAmount);
        StudentAccount.setMaxLoanAmount(maxLoanAmount);
    }
}