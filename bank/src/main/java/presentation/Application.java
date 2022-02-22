package presentation;


import models.Transfer;
import models.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AccountServices;
import service.UserServices;
import service.EmployeeServices;

import java.util.InputMismatchException;

public class Application {

    public static Logger project0 = LogManager.getLogger("chunkit_project0");

    public static void main(String[] args) {
        int userinput, userinputat2 = 0 ;
        Users one = new Users();
        UserServices appServices = new UserServices();
        AccountServices accountServices = new AccountServices();
        EmployeeServices employeeServices = new EmployeeServices();
        Transfer transfer = new Transfer();
        do {
            userinput = ManageMenu.displayMainPage();
            Boolean sw = true;
            switch (userinput) {
                case 1:
                    one = appServices.logInService();
                    userinputat2 = ManageMenu.menuManager(one);
                    project0.info("User " +one.getUserId() +" is Login");
                    //It's used for the user is waiting for approval which level is 0
                    do {
                        if(one.getlevel()==0) {
                            switch(userinputat2) {
                                case 1:
                                    project0.info("User " +one.getUserId() +" is Logout");
                                    sw = false;
                                    break;
                                case 2:
                                    try {
                                        Boolean isApplicationDone = accountServices.accountApplication(one);
                                        if(isApplicationDone) {
                                            System.out.println("The account application finished! Please wait for the approval within 24 to 48 hours");
                                        }
                                        else {
                                            System.out.println("Error! Please double check do you have any approval account or contact to our customer service , phone:xxxxxxxxx");
                                        }
                                    }
                                    catch(InputMismatchException | NumberFormatException e) {
                                        System.out.println("Please Enter the valid input");
                                    }
                                    userinputat2 = ManageMenu.menuManager(one);
                                    break;
                            }
                        }
                        //It will execute if the user is account holder which level is 1
                        else if(one.getlevel() ==1) {
                            switch(userinputat2) {
                                case 1:
                                    project0.info("User " +one.getUserId() +" is Logout");
                                    sw = false;
                                    break;
                                case 2:
                                    System.out.println("-----------------------------------------------------");
                                    String st1 = String.format("%s%1.2f", "Your Account Balance is $", accountServices.getAccountBalance(one.getUserId()) );
                                    System.out.println(st1);
                                    userinputat2 = ManageMenu.menuManager(one);
                                    break;
                                case 3:
                                    Boolean done = true;
                                    do {
                                        transfer = accountServices.createTransfer(one);
                                        if(accountServices.updateBalanceTransfer(one.getUserId(), transfer.getAmount())) {
                                            done = accountServices.transferNow(transfer);
                                        }
                                        else {
                                            System.out.println("Can not finish transfection! Please contact to our customer service , phone:xxxxxxxxx ");
                                        }
                                    }
                                    while(!done);
                                    userinputat2 = ManageMenu.menuManager(one);
                                    break;
                                case 4:
                                    int responce = -1;
                                    try {
                                        responce = accountServices.approveTransaction(one.getUserId());
                                    }
                                    catch (InputMismatchException | NumberFormatException e) {
                                        System.out.println("Please Enter a valid serves");
                                        userinputat2 = 4;
                                    }
                                    if(responce == 0) {
                                        userinputat2 = ManageMenu.menuManager(one);
                                    }
                                    break;
                                case 5:
                                    Boolean issuccess = false;
                                    try {
                                        issuccess = accountServices.depositOrWidrow(one.getUserId());
                                        if(!issuccess) {
                                            System.out.println("Please Enter the valid amount");
                                        }
                                        else if(issuccess) {
                                            System.out.println("Your transfection is complete!");
                                            userinputat2 = 2;
                                        }
                                    }
                                    catch (InputMismatchException | NumberFormatException e) {
                                        System.out.println("Please Enter Valid Amount :");
                                    }
                                    break;
                            }
                        }
                        //It will execute if the user is Employee which level is 2
                        else if(one.getlevel() == 2) {
                            switch(userinputat2) {
                                case 1:
                                    project0.info("User " + one.getUserId() +" is Logout");
                                    sw = false;
                                    break;
                                case 2:
                                    int out = -1;
                                    do {
                                        try {
                                            out =  employeeServices.approveAccount();
                                        }
                                        catch(InputMismatchException | NumberFormatException e) {
                                            System.out.println("Please Enter the valid input");
                                        }
                                    }
                                    while(out!=0) ;
                                    userinputat2 = ManageMenu.menuManager(one);

                                    break;
                                case 3:
                                    employeeServices.getAllAccount();
                                    userinputat2 = ManageMenu.menuManager(one);
                                    break;
                                case 4:
                                    //employeeServices.getAllTransfer();
                                    employeeServices.getAlllog();
                                    userinputat2 = ManageMenu.menuManager(one);
                                    break;
                                case 5:
                                    Boolean isUserCreated = appServices.creatUserService();
                                    if(isUserCreated) {
                                        System.out.println("Successfully creating a new account");
                                        userinputat2 = ManageMenu.menuManager(one);
                                    }
                                    break;
                            }
                        }
                        // efault block
                        else {
                            System.out.println("ERROR!");
                        }
                    }
                    while(sw);
                    break;
                case 2:
                    Boolean isUserCreated = appServices.creatUserService();
                    if(isUserCreated) {
                        System.out.println("New account created! Please login:");
                    }
                    break;
            }
        }
        while(userinput != 3);
    }

}
