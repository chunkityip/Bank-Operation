package service;

import DAO.UserDAO;
import DAO.EmployeeDAO;
import models.Account;
import models.Transfer;
import models.Users;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EmployeeServices {

    //log
    EmployeeDAO empDAO = new EmployeeDAO();
    UserDAO appDAO = new UserDAO();
    List<Account> accounts = new ArrayList<Account>();
    List<Transfer> transfers = new ArrayList<Transfer>();
    Users user = new Users();
    Scanner newScan = new Scanner(System.in);

    private void pandingAccount() {
        accounts =	empDAO.getPendingAccount();
        String str1 = String.format("%20s%20s%20s", "User ID", "Account ID", "Balance"  );
        System.out.println(str1);
        for(int i = 0; i< accounts.size(); i++) {
            String str2 = String.format("%20d%20d%20.2f", accounts.get(i).getUserId(),accounts.get(i).getAccountId(),accounts.get(i).getAccountBalance() );
            System.out.println(str2);
        }
    }

    public void getAllAccount() {
        accounts =	empDAO.getAllApprove();
        String str1 = String.format("%20s%20s%20s%20s%20s", "User ID", "First Name", "Last Name", "Account ID", "Balance"  );
        System.out.println(str1);
        System.out.println("================================================================================================================================================");
        for(int i = 0; i< accounts.size(); i++) {
            user = appDAO.getUsers(accounts.get(i).getUserId());
            String str2 = String.format("%20d%20s%20s%20d%20.2f", accounts.get(i).getUserId(),user.getFirstName(),user.getLastName(),accounts.get(i).getAccountId(),accounts.get(i).getAccountBalance());
            System.out.println(str2);
        }
    }

    // chekes if the account request is exists
    // it will return 0 if request dose not exists
    // it will retrun the int array with index 0 being account ID and index 1 with userid

    public int[] isRequestExists(List<Account> one, int accountID ) {

        int[] userinfo = new int[2];
        for(int i =0; i< one.size(); i++) {
            if(one.get(i).getAccountId() == accountID) {
                userinfo[0] = accountID;
                userinfo[1] = one.get(i).getUserId();
            }
        }
        return userinfo;
    }

    // this will ask for user input to approve the account
    public int approveAccount() throws InputMismatchException, NumberFormatException {
        int[] userinfo = new int[2];
        int ans;
        do {
            pandingAccount();
            System.out.println("Please Enter the AccountID to Approve / reject or 0 to exit ");
            ans = Integer.parseInt(newScan.nextLine());
            userinfo = isRequestExists(accounts, ans);
            if(userinfo[0] !=0 && userinfo[1] != 0) {
                System.out.println("please enter 1 for Approve 2 for reject");
                int apprej =Integer.parseInt(newScan.nextLine()) ;
                if(apprej> 0 & apprej <3) {
                    if(empDAO.approveAccount(userinfo[0], apprej) & empDAO.approveCustomerAccount(userinfo[1], apprej) )   {
                        System.out.println("Account "+ ans +" has been Successfully updated ");
                    }
                }
            }
            //pandingAccount();
        }
        while(ans != 0);
        return ans;
    }
}
