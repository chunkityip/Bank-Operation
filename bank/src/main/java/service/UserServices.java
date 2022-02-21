package service;

import DAO.UserDAO;

import models.Users;

import java.util.Scanner;

public class UserServices {

    UserDAO one = new UserDAO();
    Users user = new Users();
    Scanner newScan = new Scanner(System.in);


    // this method will ask for the username and password check in database if exist then return instance of the user object

    public Users logInService() {

        int uid;
        do {
            System.out.println("Please Enter Username :");
            String userName = newScan.nextLine();
            System.out.println("Please Enter  Password :");
            String passWord = newScan.nextLine();

            uid = one.checkLogin(userName, passWord);

            if (uid == -1) {
                System.out.println("Pleae Try again, User not found");

            }

        }
        while(uid == -1);
        return user = one.getUsers(uid);



    }

    // this will ask for the first name , last name, username, and password to create new user in the db if user created will return true if error then false
    // it will not let creat duplicate username.

    public Boolean creatUserService() {
        Boolean isUserCreated;

        String uname;
        System.out.println("Please Enter First Name :");
        user.setFirstName(newScan.nextLine());
        System.out.println("Please Enter Last Name :");
        user.setLastName(newScan.nextLine());

        do {

            System.out.println("Please Enter Username:");
            uname = newScan.nextLine();
            if(!one.isUserUnique(uname)) {

                System.out.println("Username already in the system ");
            }

        }
        while(!one.isUserUnique(uname));
        user.setUserName(uname);

        System.out.println("Please Enter Password");
        user.setPssWord(newScan.nextLine());
        user.setSecurityLevel(0);

        isUserCreated = one.creatLogin(user);

        return isUserCreated;

    }


}
