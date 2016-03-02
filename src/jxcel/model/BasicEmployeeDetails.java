package jxcel.model;

/**
 * Created by kumars on 2/29/2016.
 */
public class BasicEmployeeDetails {
    private String name;
    private String empId;
    private String salesForceId;
    private String emailId;

    public String getSalesForceId() {
        return salesForceId;
    }

    public void setSalesForceId(String salesForceId) {
        this.salesForceId = salesForceId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }


    public void displayBasicInfo() {
        System.out.print("EmpId: " + this.getEmpId());
        System.out.print("\tEmpName: " + this.getName());
        System.out.print("\tEmpEmailId: " + this.getEmailId());
        System.out.println("\tEmpSalesForce: " + this.getSalesForceId());
    }


}
