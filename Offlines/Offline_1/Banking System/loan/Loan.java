package loan;

enum Status {
    PENDING,
    APPROVED,
    REJECTED,
    PAID
}

public class Loan {

    private String applicantName;
    private double amount;
    private String approverName;
    private Status status;
    private int yearsSinceApproval;

    // Constructor
    public Loan(String applicantName, double amount) {
        this.applicantName = applicantName;
        this.amount = amount;
        this.approverName = "";
        this.status = Status.PENDING;
        this.yearsSinceApproval = 0;
    }

    // Getter methods
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

    // Setter methods
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
}
