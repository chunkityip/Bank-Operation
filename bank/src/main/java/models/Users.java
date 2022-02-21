package models;

public class Users {

    private String firstName;
    private String lastName;
    private String userName;
    private String pssWord;
    private int level;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Users(String firstName, String lastName, String userName, String pssWord) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.pssWord = pssWord;
        this.level = 0;

    }

    public int getlevel() {
        return level;
    }

    public Users() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPssWord() {
        return pssWord;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecurityLevel(int securityLevel) {
        this.level = securityLevel;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPssWord(String pssWord) {
        this.pssWord = pssWord;
    }

}
