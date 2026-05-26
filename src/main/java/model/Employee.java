package model;

public class Employee extends Person {

    private String jobPosition;
    private String hireDate;

    public Employee(String id, String name, int age, double height, double weight, String jobPosition) {
        super(id, name, age, height, weight);
        this.jobPosition = jobPosition;
        this.hireDate = "";
    }

    public Employee(String id, String name, int age, double height, double weight, String jobPosition, String hireDate) {
        super(id, name, age, height, weight);
        this.jobPosition = jobPosition;
        this.hireDate = hireDate;
    }

    @Override
    public String getRoleDescription() {
        return "Empleado, puesto: " + jobPosition;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
