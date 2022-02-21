package models;

public class Account {

    private int accountId;
    private int userId;
    private double accountBalance;
    private int accountStatus;

    public Account() {
        super();
    }

    public Account(int accountId, int userId, double accountBalance, int accountStatus) {
        super();
        this.accountId = accountId;
        this.userId = userId;
        this.accountBalance = accountBalance;
        this.accountStatus = accountStatus;
    }

    public Account(int accountId, int userId, double accountBalance) {
        super();
        this.accountId = accountId;
        this.userId = userId;
        this.accountBalance = accountBalance;
        this.accountStatus = 0;
    }
    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public double getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(double accountBalance) {

        if(accountBalance >= 0) {
            this.accountBalance = accountBalance;
        }
    }
    public int getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

}
