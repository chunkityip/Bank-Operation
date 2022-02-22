package DAO;

import models.Account;
import models.Transfer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Statement st;
    private PreparedStatement pst;

    private DBUtil mcf = DBUtil.getConnectionFactory();
    Connection conn = mcf.getConnection();

    //getPendingAccount() method is to get the pending account from the database
    public List<Account> getPendingAccount(){
        List<Account> myAccounts = new ArrayList<Account>();
        String sql ="Select account_id, user_id, balance from account where status = 0 ";
        try{
            pst = conn.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                Account accounts = new Account();
                accounts.setAccountId(rst.getInt(1));
                accounts.setUserId(rst.getInt(2));
                accounts.setAccountBalance(rst.getDouble(3));
                myAccounts.add(accounts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myAccounts;
    }

    // getAllApprove() will get all the active account.
    public List<Account> getAllApprove(){
        List<Account> myAccounts = new ArrayList<Account>();
        String sql ="Select account_id, user_id, balance from account where status = 1 ";
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                Account accounts = new Account();
                accounts.setAccountId(rst.getInt(1));
                accounts.setUserId(rst.getInt(2));
                accounts.setAccountBalance(rst.getDouble(3));
                myAccounts.add(accounts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myAccounts;
    }

    // approveAccount() method update the account table for the status of the account
    public Boolean approveAccount(int accountId, int status) {
        Boolean isAccountApproved = false;
        String sql = "update account set status = ? where account_id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, status);
            pst.setInt(2, accountId);
            int rst = pst.executeUpdate();
            if(rst != 0) isAccountApproved = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAccountApproved;
    }

    // approveCustomerAccount() method update the user table for the level( 0 to 2) for the manu change
    public Boolean approveCustomerAccount(int uid, int status) {
        Boolean isAccountApproved = false;
        String sql = "update users set level = ? where user_id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, status);
            pst.setInt(2, uid);
            int rst = pst.executeUpdate();
            if(rst != 0) isAccountApproved = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAccountApproved;
    }

    // getAllTransfer() method show all the transfer to employee
    public List<Transfer> getAllTransfer(){
        List<Transfer> transfer = new ArrayList<Transfer>();
        //TransactionId , FromAccountId , ToAccountId , Amount , Approval
        String sql = "Select * from transfer ";
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                Transfer accounts = new Transfer();
                accounts.setTransactionId(rst.getInt(1));
                accounts.setFromAccountId(rst.getInt(2));
                accounts.setToAccountId(rst.getInt(3));
                accounts.setAmount(rst.getDouble(4));
                accounts.setApproval(rst.getInt(5));
                transfer.add(accounts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transfer;
    }
}
