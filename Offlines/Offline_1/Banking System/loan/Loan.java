package loan;

enum Status {
    PENDING,
    APPROVED,
    REJECTED,
    PAID
}

public class Loan {
    private static final double LOAN_INTEREST_RATE = 10;

    private String applicantName;
    private double amount;
    private String approverName;
    private Status status;
    private int yearsSinceApproval;

    public Loan(String applicantName, double amount) {
        this.applicantName = applicantName;
        this.amount = amount;
        this.approverName = "";
        this.status = Status.PENDING;
        this.yearsSinceApproval = -1;
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

    public int getYearsSinceApproval() {
        return yearsSinceApproval;
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

    public void setYearsSinceApproval(int yearsSinceApproval) {
        this.yearsSinceApproval = yearsSinceApproval;
    }

    public double getLoanInterest(){
        return amount*LOAN_INTEREST_RATE/100.0;
    }
}
