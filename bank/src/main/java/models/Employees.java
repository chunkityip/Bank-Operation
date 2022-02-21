package models;

public class Employees extends Users{
    private int employeeId;

    public Employees(String firstName, String lastName, String userName, String pssWord, int employeeId) {
        super(firstName, lastName, userName, pssWord);
        // TODO Auto-generated constructor stub
        this.employeeId = employeeId;
    }

}
