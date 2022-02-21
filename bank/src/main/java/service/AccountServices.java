package service;

import DAO.CustomerAccountDAO;
import models.Account;
import models.Transfer;
import models.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AccountServices {

    CustomerAccountDAO customerAccountDAO = new CustomerAccountDAO();
    Account account = new Account();
    Transfer transfer = new Transfer();
    List<Transfer> transfers = new ArrayList<Transfer>();
    Scanner newScan = new Scanner(System.in);
    public Logger project0 = LogManager.getLogger("project0");

    // this method request MyAppDAO to create the account with the balance if fails return false
    // Success will return true
    // takes the user object as the input

    public Boolean accountApplication(Users one) throws InputMismatchException, NumberFormatException {
        double balance = -1;
        if(customerAccountDAO.isAccountUnique(one.getUserId())) {
            account.setUserId(one.getUserId());
            do {

                System.out.println("Please enter initial account balance : ");
                try {

                    balance = Double.parseDouble(newScan.nextLine());
                    account.setAccountBalance(balance);
                }
                catch(InputMismatchException e) {

                    e.getMessage();
                }
            }
            while(balance < 0);
            return customerAccountDAO.creatAccount(account);
        }
        return false;
    }

    // this takes all the input form the users and creates the instance of MyTransfer and checks the user input
    // takes the user instance as argument.
    public Transfer createTransfer(Users one) {
        int reciveAccount = 0;
        double amount = 0;
        do {
            try {
                System.out.println("Please enter active recipient account number");
                reciveAccount =Integer.parseInt(newScan.nextLine());
            }
            catch(InputMismatchException | NumberFormatException e){
                System.out.println("Please enter the valid input");
            }
            if(customerAccountDAO.isAccountAvailable(reciveAccount) && customerAccountDAO.findAccountId(one.getUserId()) != -1) {
                transfer.setFromAccountId(customerAccountDAO.findAccountId(one.getUserId()));
                transfer.setToAccountId(reciveAccount);
                System.out.println("Please Enter the Amount need to be transfer:");
                try {
                    amount =  Double.parseDouble(newScan.nextLine());
                }
                catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Please enter the valid input");
                }
                transfer.setAmount(amount);
                transfer.setApproval(0);
            }
        }
        while(!customerAccountDAO.isAccountAvailable(reciveAccount) || customerAccountDAO.findAccountId(one.getUserId()) == -1);
        project0.info("User " + one.getUserId() +" Created Transfer request of $"+ transfer.getAmount());
        return transfer ;
    }

    // return false if transfer unsuccessful  to insert in the database else true
    public Boolean transferNow(Transfer transfer) {
        Boolean done = false;
        if(customerAccountDAO.transfer(transfer)) {
            done = true;
        }
        return done;
    }

    // get the account balance of particulate userid
    public double getAccountBalance(int userid) {
        return customerAccountDAO.getBalance(userid);
    }

    // this will update the user account ballence.

    public Boolean updateBallenceTransfer(int userid, double updateBy) {
        Boolean neg = false;
        double update = getAccountBalance(userid) - updateBy;
        if(update >= 0 ) {
            customerAccountDAO.updateBalance(update, userid);
            neg = true;
        }
        return neg;
    }
    // this will update the accepted transaction
    public Boolean updateBAllenceTransferAccept(int userid, double updateBy) {
        Boolean neg = false;
        double update = getAccountBalance(userid) + updateBy;
        if(update >= 0 ) {
            customerAccountDAO.updateBalance(update, userid);
            neg = true;
        }
        return neg;
    }
    // Display all the upapprove transection for the user
    private int pandingTransection(int accountid) {
        transfers = customerAccountDAO.getPendingTransfer(accountid);
        String str1 = String.format("%20s%20s%20s", "Transfer ID", "From Account Id", "Amount" );
        System.out.println(str1);
        System.out.println("########################################################################################################################################");
        for(int i = 0; i< transfers.size(); i++) {
            //System.out.println("");
            String str2 = String.format("%20d%20d%20.2f", transfers.get(i).getTransactionId(),transfers.get(i).getFromAccountId(),transfers.get(i).getAmount());
            System.out.println(str2);
            //System.out.println("Transfer ID : "+transfers.get(i).getTransactionId()+" From Account ID : "+transfers.get(i).getFromAccountId()+" Amount : "+transfers.get(i).getAmount());
        }
        return transfers.size();
    }

    private Transfer isTransferExists(List<Transfer> transfers, int transferID ) {
        for(int i =0; i< transfers.size(); i++) {
            if(transfers.get(i).getTransactionId() == transferID) {
                transfer = transfers.get(i);
            }
        }
        return transfer;
    }


    // this method approve and reject the incoming transfer for the particulate user
    public int approveTransection(int toUserId) throws InputMismatchException, NumberFormatException {
        Boolean transfersuccess = false ;
        int accountid = customerAccountDAO.findAccountId(toUserId);
        int ans = pandingTransection(accountid);
        while(ans != 0) {
            System.out.println("Please Enter the Transfer ID to Approve / reject or 0 to exit ");
            ans = Integer.parseInt(newScan.nextLine()) ;
            transfer = isTransferExists(transfers, ans);
            if(transfer.getFromAccountId() !=0 && ans != 0) {
                System.out.println("please enter 1 for Approve 2 for reject");
                int apprej = newScan.nextInt();
                if(apprej> 0 & apprej <3) {
                    // make sure bool account table and transection table is updated
                    if(apprej == 1) {
                        //int toUserid = accountDAO.getUserIdByAccountId(accountid);
                        transfersuccess = updateBAllenceTransferAccept(toUserId, transfer.getAmount()  );
                        project0.info("User " + toUserId +" Accepted the Transfer $"+ transfer.getAmount());
                    }
                    if(apprej == 2) {
                        int formUserid = customerAccountDAO.getUserIdByAccountId(transfer.getFromAccountId());
                        if(formUserid != -1) {
                            project0.info("User " + toUserId +" Rejected the Transfer $"+ transfer.getAmount());
                            transfersuccess = updateBAllenceTransferAccept(formUserid, transfer.getAmount());
                            project0.info("User " + formUserid +" Account has been Credited $"+ transfer.getAmount());
                        }
                    }
                }
                if(transfersuccess) {
                    Boolean upddated = customerAccountDAO.updateTransferTable(transfer, apprej);
                }
                ans = pandingTransection(accountid);
            }
        }
        if(ans == 0 ) {
            System.out.println("There are no transection avilable to approve");
        }
        return ans;
    }

    public Boolean depositOrWidrow(int userid) throws InputMismatchException, NumberFormatException {
        Boolean success = false;
        System.out.println("Please Enter Amount to widow or deposit");
        double amount = Double.parseDouble(newScan.nextLine());
        if(amount > 0) {
            success =	updateBAllenceTransferAccept(userid, amount);
            project0.info("User " + userid +" Deposited $"+amount);
        }
        else if(amount < 0) {
            success = updateBAllenceTransferAccept(userid, amount);
            project0.info("User " + userid +" Widraw $"+amount);
        }
        return success;
    }
}
