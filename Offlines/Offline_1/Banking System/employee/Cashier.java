package employee;

import account.*;

public class Cashier implements Employee{
    private String name;

    public Cashier(String name) {
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
}