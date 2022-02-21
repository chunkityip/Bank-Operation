package DAO;

import models.Account;
import models.Transfer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerAccountDAO {
    private Statement st;
    private PreparedStatement pst;

    //cf mean Connection Function
    private DBUtil cf = DBUtil.getConnectionFactory();
    Connection conn = cf.getConnection();

    /*
    creatAccount() method will return:
    1. return true if the account created
    2. return false if the account does not create/existed,
    and it will take the instance of the account object.
     */
    public Boolean creatAccount(Account account) {
        Boolean accountCreated = true;
        String sql = "insert into account (user_id, balance, status)"
                +"values(?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, account.getUserId());
            pst.setDouble(2, account.getAccountBalance());
            pst.setInt(3, account.getAccountStatus());
            int res = pst.executeUpdate();
            if(res == 0) accountCreated = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountCreated;
    }

    /*
    isAccountUnique() method will return:
    1. return true if the account request is in the database
    2. return false if account request does not in the database,
    and it takes user_id as input.
     */
    public Boolean isAccountUnique(int uid) {
        Boolean isAccoutunique = true;
        String sql = "select * from account where user_id = ? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, uid);
            ResultSet res = pst.executeQuery();
            if(res.next()) {
                isAccoutunique  = false ;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isAccoutunique;
    }


    /*
    isAccountAvailable() method will :
    1. returns true if an active account is available
    2. return false if an active account is "not" available.
    It takes into account id.
     */
    public Boolean isAccountAvailable(int accountid) {
        Boolean isAccoutunique = false;
        String sql = "select * from account where account_id = ? and status = 1 ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, accountid);
            ResultSet res = pst.executeQuery();
            if(res.next()) {
                isAccoutunique  = true ;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isAccoutunique;
    }

    /*
    findAccountId() returns account_id ,
    it takes user id if account not found or not , returns -1 if not approved.
     */
    public int findAccountId(int uid) {
        int accountNumber = -1 ;
        String sql = "select account_id from account where user_id =? and status = 1";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, uid);
            ResultSet res = pst.executeQuery();
            if(res.next()) {
                accountNumber = res.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return accountNumber;
    }

    //transfer() method returns true if transfer request successfully added to the database
    public Boolean transfer(Transfer transfer) {
        Boolean done = false;

        String sql = "insert into transfer (from_account_acid, to_account_acid, amount, approval)"+
                "values(?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, transfer.getFromAccountId());
            pst.setInt(2, transfer.getToAccountId());
            pst.setDouble(3, transfer.getAmount());
            pst.setInt(4, transfer.getApproval());
            int rst = pst.executeUpdate();
            if(rst != 0) {
                done = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return done;
    }

    // updateTransferTable() update the transaction approve in the database , if success returns true , else false.
    public Boolean updateTransferTable(Transfer transfer, int responce) {
        Boolean done = false;
        String sql = "update transfer set approval = ? where transaction_id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, responce);
            pst.setInt(2, transfer.getTransactionId());
            int rst = pst.executeUpdate();
            if(rst != 0) {
                done = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return done;
    }

    // getBalance() return the account balance of particulate user
    public double getBalance(int userId) {
        double balance = 0 ;
        String sql = "select balance from account where user_id = ? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rest = pst.executeQuery();
            if(rest.next()) {
                balance = rest.getDouble(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return balance;
    }

    //updateBalance() return the update information (new balance) to database
    public Boolean updateBalance(double balance, int userid  ) {
        Boolean done = false;
        String sql = "Update account set balance = ? where user_id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setDouble(1, balance);
            pst.setInt(2, userid);
            int rst = pst.executeUpdate();
            if(rst != 0) done = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return done;
    }

    //getPendingTransfer() method return the information of pending transfer
    public List<Transfer> getPendingTransfer(int accountId){
        List<Transfer> transfer = new ArrayList<Transfer>();
        String sql ="Select transaction_id, from_account_acid, to_account_acid, amount from transfer where approval = 0 and to_account_acid = ? and amount > 0 ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, accountId);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                Transfer accounts = new Transfer();
                accounts.setTransactionId(rst.getInt(1));
                accounts.setFromAccountId(rst.getInt(2));
                accounts.setToAccountId(rst.getInt(3));
                accounts.setAmount(rst.getDouble(4));
                transfer.add(accounts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transfer;
    }
    /*
    getUserIdByAccountId() will return -1 if user_id not found,
    and this will find the userid from the account id;
     */
    public int getUserIdByAccountId(int accountId) {
        int userid = -1;
        String sql ="Select user_id from account where account_id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, accountId);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                userid = rst.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userid;
    }
}
