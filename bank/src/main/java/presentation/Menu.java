package presentation;

public class Menu {

    //Display Main Login Page
    public String loginMenu() {
        return "-----------------------Welcome to CBANK-----------------------"+ "\n" +
                "1. Login"+ "\n" +
                //"2. Create Account" + "\n" +//
                "\"Please select number 1 to enter login page: \" : ";
    }

    //Display User Menu
    public String userMenu() {

        return "-----------------------User Login-----------------------"+ "\n" +
                "1. Logout"  +"\n"+
                "2. Apply for Account " +"\n"+
                "Please select the service : ";
    }

    //Display User Customer Menu
    public String customerMenu() {

        return "-----------------------Welcome-----------------------\n" +
                "1. Logout \n"+
                "2. View Account Balance \n"+
                "3. Make Transfer \n"+
                "4. Accept Transfer \n"+
                "5. Deposit or Withdrew Money \n"+
                "Please select the service(1 to 5): ";
    }

    //Display User Employee Menu
    public String employeeMenu() {

        return "-----------------------Welcome-----------------------\n" +
                "1. Logout \n"+
                "2. View Account Request \n"+
                "3. view Customer Account \n"+
                "4. View All Transactions \n"+
                "5. Creating A New Account For New Customer \n" +
                "Please select the service(1 to 5): ";
                //-----------------------------------------------------
    }
}
