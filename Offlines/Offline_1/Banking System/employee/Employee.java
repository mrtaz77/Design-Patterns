package employee;

import account.Account;

public abstract class Employee {
    protected String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double lookUp(Account[] accounts,String name) {
        for (Account account : accounts) {
            if(account.getName().equals(name))return account.queryDeposit();
        }
        return -1;
    }
}