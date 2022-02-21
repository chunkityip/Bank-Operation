package presentation;

import models.Users;

import java.util.Scanner;

public class ManageMenu {

    static Menu myMenu = new Menu();
    static Scanner myScanner = new Scanner(System.in);

    // this method validate the user input and display the login option.
    public static int displayMainPage()   {
        int userInput = 0;
        Boolean swi = true;
        while(swi) {
            try {
                System.out.println(myMenu.loginMenu());
                userInput =Integer.parseInt(myScanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Please Enter valid choice");
            }
            if (userInput > 0 && userInput <=2 ) {
                swi = false;
            }
        }
        return userInput;
    }

    public static int menuManager(Users one) {


        //0 mean waiting for pending , 1 mean is Customer , 2 mean is Employee
        if(one.getlevel() == 0) {
            return displayUserMenu(one);
        }
        else if (one.getlevel()== 1) {
            return displayCustomerMenu(one);
        }
        else if(one.getlevel()==2) {
            return displayEmployeeMenu(one);
        }
        else {
            System.out.println("User dose not exist");
        }
        return 1;
    }

    public static int displayUserMenu(Users one) {
        int userInput =0 ;
        Boolean swi = true;
        while(swi) {
            System.out.println("Welcome, "+one.getFirstName()+" "+one.getLastName());
            try {
                System.out.println(myMenu.userMenu());
                userInput =Integer.parseInt(myScanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Please Enter valid choice");
            }
            if (userInput > 0 && userInput <=2 ) {
                swi = false;
            }
        }
        return userInput;
    }
    public static int displayCustomerMenu(Users one) {
        int userInput = 0;
        Boolean swi = true;
        System.out.println("Customer "+one.getFirstName()+" "+one.getLastName());

        while(swi) {
            try {
                System.out.println(myMenu.customerMenu());
                userInput =Integer.parseInt(myScanner.nextLine());
            }
            catch (NumberFormatException e) {
                // TODO: handle exception
                System.out.println("Please Enter valid choice");
            }
            if(userInput > 0 && userInput <= 5) {
                swi = false;
            }
        }

        return userInput;
    }

    public static int displayEmployeeMenu(Users one) {
        int userInput = 0;
        Boolean swi = true;
        while(swi) {
            System.out.println("Employee "+one.getFirstName()+" "+one.getLastName());
            try {
                System.out.println(myMenu.employeeMenu());
                userInput =Integer.parseInt(myScanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Please Enter valid choice");
            }
            if(userInput > 0 && userInput <= 6) {
                swi = false;
            }
        }
        return userInput;
    }
}
