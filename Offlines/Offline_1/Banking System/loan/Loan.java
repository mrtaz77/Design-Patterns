package loan;

public class Loan {
    private double loanInterestRate;
    private String applicantName;
    private double amount;
    private String approverName;
    private Status status;
    
    public Loan(String applicantName, double amount,double loanInterestRate) {
        this.applicantName = applicantName;
        this.amount = amount;
        this.approverName = "";
        this.status = Status.PENDING;
        this.loanInterestRate = loanInterestRate;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public double getAmount() {
        return amount;
    }

    public String getApproverName() {
        return approverName;
    }

    public Status getStatus() {
        return status;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getLoanInterestRate() { return loanInterestRate; }
    public void setLoanInterestRate(double loanInterestRate){
        this.loanInterestRate = loanInterestRate; 
    }

    public double getLoanInterest(){
        return amount*loanInterestRate/100.0;
    }
}
