package DAO;

import models.Users;

import java.sql.*;

public class UserDAO {

    private Statement st;
    private PreparedStatement pst;

    private DBUtil mcf = DBUtil.getConnectionFactory();
    Connection conn = mcf.getConnection();

    /*
    checkLogin() method checks if the user already in the database and compare the login , password ,finally, return to the user id
    if user and password is fail , return -1
    if user and password is true return user_id form the table
     */
    public int checkLogin(String uname, String upass) {

        int uid = -1; // login is not successfully

        try {
            String sql =  "select user_id from users where user_name = ? and pssword = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, uname);
            pst.setString(2, upass);
            ResultSet res = pst.executeQuery();
            if(res.next()) {
                uid = res.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uid;
    }

    /*
    getUsers() method will:
    1. Getting the first name and last name of the user form the user table.
    2. Setting the uid to ResultSet , it iterated over to extract the data
     */
    public Users getUsers(int uid) {
        Users newUser = new Users();

        String sql = "select first_name, last_name, level, user_id from users where user_id = ?";

        try {
            //st = conn.createStatement();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, uid);
            ResultSet res = pst.executeQuery();
            if(res.next()) {
                newUser.setFirstName(res.getString(1));
                newUser.setLastName(res.getString(2));
                newUser.setSecurityLevel(res.getInt(3));
                newUser.setUserId(res.getInt(4));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newUser;
    }

    // isUserUnique() method checks if the username already exists in the database, it will return false if not , else will return true.
    public Boolean isUserUnique(String userName) {
        Boolean ans = true;
        String sql = "select user_name from users where user_name = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet res = pst.executeQuery();
            if(res.next()) {
                ans  = false ;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ans;
    }

    //creatLogin() method create a login page for user
    public Boolean creatLogin(Users user) {
        Boolean userCreated = true;
        String sql = "insert into users (first_name, last_name, user_name, pssword, level)"
                +"values(?, ?, ?, ?, ?) ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3,  user.getUserName());
            pst.setString(4, user.getPssWord());
            pst.setInt(5, user.getlevel());
            int res = pst.executeUpdate();
            if(res == 0) userCreated = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userCreated;
    }
}
