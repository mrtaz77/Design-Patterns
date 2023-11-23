import java.io.*;

import bank.*;

public class FileIO {

    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output.txt";


    public static void main(String[] args) {
        try(BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE))){
            BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT_FILE));

            // Initialize static variables from Main
            double fixedDepositBalanceInterestRate = 15;
            double fixedDepositServiceCharge = 500;
            double fixedDepositMinInitBalance = 100000;
            double fixedDepositMinDeposit = 50000;
            int fixedDepositMinWithdrawalAge = 1;
            double fixedDepositMaxLoanAmount = 100000;

            double loanInterestRate = 10;

            double savingsBalanceInterestRate = 10;
            double savingsServiceCharge = 500;
            double savingsMinBalanceOnWithdrawal = 1000;
            double savingsMaxLoanAmount = 10000;

            double studentBalanceInterestRate = 5;
            double studentMaxWithdrawalAmount = 500;
            double studentMaxLoanAmount = 1000;

            double initialFund = 100000;

            int numberOfOfficers = 2;
            int numberOfCashiers = 5;

            BankManagement.initialize(
                // Parameters for FixedDepositAccount class
                fixedDepositBalanceInterestRate, 
                fixedDepositServiceCharge,
                fixedDepositMinInitBalance, 
                fixedDepositMinDeposit,
                fixedDepositMinWithdrawalAge, 
                fixedDepositMaxLoanAmount,

                // Parameters for SavingsAccount class
                savingsBalanceInterestRate, 
                savingsServiceCharge,
                savingsMinBalanceOnWithdrawal, 
                savingsMaxLoanAmount,

                // Parameters for StudentAccount class
                studentBalanceInterestRate, 
                studentMaxWithdrawalAmount, 
                studentMaxLoanAmount    
            );

            var bankManagement = new BankManagement(initialFund,loanInterestRate);

            // initialize employees
            bankManagement.createEmployee("MD", "Managing Director");

            for(int i = 1; i <= numberOfCashiers; i++) {
                bankManagement.createEmployee("C"+String.valueOf(i), "Cashier");
            }

            for(int i = 1; i <= numberOfOfficers; i++) {
                bankManagement.createEmployee("O"+String.valueOf(i), "Officer");
            }

            out.write("Bank Created; MD, O1, O2, C1, C2, C3, C4, C5 created\n");
            
            while(true){
                var input = in.readLine();
                if(input == null)break;
                String[] tokens = input.split(" ");

                String command = tokens[0];

                switch(command){
                    // new account
                    case "Create" -> {
                        String name = tokens[1];
                        String accountType = tokens[2];
                        double amount = Double.parseDouble(tokens[3]);
                        try{
                            bankManagement.createAccount(
                                name, 
                                amount, 
                                accountType    
                            );
                        }catch(Exception e){
                            out.write(e.getMessage()+"\n");
                        }

                        String accountCommand = "";

                        while(!accountCommand.equals("Close")){
                            var inputAccount = in.readLine();

                            String[] accountTokens = inputAccount.split(" ");

                            accountCommand = accountTokens[0];

                            // menu for account

                            try{
                                switch(accountCommand){
                                    case "Query" -> {
                                        double deposit = bankManagement.queryDepositByName(name);
                                        double loan = bankManagement.queryApprovedLoansByName(name);
                                        
                                        var output = "Current Balance " + deposit + "$";
                                        if(loan > 0)output += ", loan " + loan + "$\n";
                                        else output += "\n";

                                        out.write(output);
                                    }

                                    case "Withdraw" -> {
                                        var withdrawalAmount = Double.parseDouble(accountTokens[1]);
                                        try{
                                            bankManagement.withdraw(name, withdrawalAmount);
                                            var output = "Successful transaction; current balance "+ bankManagement.queryDepositByName(name) + "$\n";
                                            out.write(output);                                            
                                        }catch(Exception e){
                                            var output = "Invalid transaction; current balance "+ bankManagement.queryDepositByName(name) + "$\n";
                                            System.out.println(e.getMessage());
                                            out.write(output);
                                        }
                                    }

                                    case "Request" -> {
                                        var loanAmount = Double.parseDouble(accountTokens[1]);

                                        try{
                                            bankManagement.requestLoan(name, loanAmount);
                                            var output = "Loan request successful, sent for approval\n";
                                            out.write(output);
                                        }catch(Exception e){
                                            var output = "Loan request failed; current balance "+ bankManagement.queryDepositByName(name) + "$\n";
                                            System.out.println(e.getMessage());
                                            out.write(output);
                                        }
                                    }

                                    case "Deposit" -> {
                                        var depositAmount = Double.parseDouble(accountTokens[1]);

                                        try{
                                            bankManagement.deposit(name, depositAmount);
                                            var output = depositAmount + "$ deposited; current balance "+bankManagement.queryDepositByName(name)+"$\n";
                                            out.write(output);
                                        }catch(Exception e){
                                            var output = "Deposit failed; current balance "+ bankManagement.queryDepositByName(name) + "$\n";
                                            System.out.println(e.getMessage());
                                            out.write(output);
                                        }
                                    }
                                    
                                    case "Close" -> {
                                        out.write("Transaction Closed for "+tokens[1]+"\n");
                                    }
                                    default -> out.write("Unknown Command\n");
                                
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    case "INC" -> {
                        bankManagement.inc();
                        out.write("1 year passed\n");
                    }

                    case "Open" -> {
                        var name = tokens[1];

                        var account = bankManagement.getAccountByName(name);

                        if(account != null){
                            out.write("Welcome back, "+name+"\n");

                            String accountCommand = "";

                            while(!accountCommand.equals("Close")){
                                var inputAccount = in.readLine();

                                String[] accountTokens = inputAccount.split(" ");

                                accountCommand = accountTokens[0];

                                // menu for account

                                try{
                                    switch(accountCommand){
                                        case "Query" -> {
                                            double deposit = bankManagement.queryDepositByName(name);
                                            double loan = bankManagement.queryApprovedLoansByName(name);
                                            
                                            var output = "Current Balance " + deposit + "$";
                                            if(loan > 0)output += ", loan " + loan + "$\n";
                                            else output += "\n";

                                            out.write(output);
                                        }

                                        case "Withdraw" -> {
                                            var withdrawalAmount = Double.parseDouble(accountTokens[1]);
                                            try{
                                                bankManagement.withdraw(name, withdrawalAmount);
                                                var output = "Successful transaction; current balance "+ bankManagement.queryDepositByName(name) + "$\n";
                                                out.write(output);                                            
                                            }catch(Exception e){
                                                var output = "Invalid transaction; current balance "+ bankManagement.queryDepositByName(name) + "$\n";
                                                System.out.println(e.getMessage());
                                                out.write(output);
                                            }
                                        }

                                        case "Request" -> {
                                            var loanAmount = Double.parseDouble(accountTokens[1]);

                                            try{
                                                bankManagement.requestLoan(name, loanAmount);
                                                var output = "Loan request successful, sent for approval\n";
                                                out.write(output);
                                            }catch(Exception e){
                                                var output = "Loan request failed; current balance "+ bankManagement.queryDepositByName(name) + "$\n";
                                                System.out.println(e.getMessage());
                                                out.write(output);
                                            }
                                        }

                                        case "Deposit" -> {
                                            var depositAmount = Double.parseDouble(accountTokens[1]);

                                            try{
                                                bankManagement.deposit(name, depositAmount);
                                                var output = depositAmount + "$ deposited; current balance "+bankManagement.queryDepositByName(name)+"$\n";
                                                out.write(output);
                                            }catch(Exception e){
                                                var output = "Deposit failed; current balance "+ bankManagement.queryDepositByName(name) + "$\n";
                                                System.out.println(e.getMessage());
                                                out.write(output);
                                            }
                                        }
                                        
                                        case "Close" -> {
                                            out.write("Transaction Closed for "+tokens[1]+"\n");
                                        }
                                        default -> out.write("Unknown Command\n");
                                    
                                    }
                                }catch(Exception e){
                                    out.write(e.getMessage()+"\n");
                                    e.printStackTrace();
                                }
                            }
                        }
                        else{
                            var employee = bankManagement.getEmployeeByName(name);

                            if(employee != null){
                                var output = name+" active";
                                if(bankManagement.getPendingLoanUserNames().length() > 0)output += ", there are loan approvals pending";
                                out.write(output+"\n");

                                String employeeCommand = "";

                                while(!employeeCommand.equals("Close")){
                                    var inputEmployee = in.readLine();

                                    String[] employeeTokens = inputEmployee.split(" ");

                                    employeeCommand = employeeTokens[0];

                                    try{
                                        switch(employeeCommand){
                                            case "Approve" -> {
                                                output = bankManagement.approveLoan(name);
                                                out.write(output+"\n");
                                            }

                                            case "Lookup" -> {
                                                var accountName = employeeTokens[1];

                                                output = bankManagement.lookUp(name,accountName);

                                                out.write(output+"\n");
                                            }

                                            case "Change" -> {
                                                var accountType = employeeTokens[1];
                                                var newInterestRate = Double.parseDouble(employeeTokens[2]);

                                                output = bankManagement.change(name,accountType,newInterestRate);

                                                out.write(output+"\n");
                                            }

                                            case "See" -> {
                                                output = bankManagement.see(name);
                                                out.write(output+"\n");
                                            }

                                            case "Close" -> {
                                                out.write("Operations Closed for "+tokens[1]+"\n");
                                            }
                                            default -> out.write("Unknown command\n");    
                                        }
                                    }catch(Exception e){
                                        e.printStackTrace();
                                        out.write(e.getMessage()+"\n");
                                    }
                                }
                            }else{
                                out.write("Unknown User\n");
                            }
                        }
                        }
                    default -> out.write("Invalid User\n");
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
