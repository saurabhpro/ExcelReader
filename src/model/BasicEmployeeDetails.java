package model;

import emplmasterrecord.EmployeeMasterData;

/**
 * Created by kumars on 2/29/2016.
 */
public class BasicEmployeeDetails {
	private String name;
	private String empId;
	private String salesForceId;
	private String emailId;

	public void displayBasicInfo() {
		System.out.print("EmpId: " + this.getEmpId());
		System.out.print("\tEmpName: " + this.getName());
		System.out.print("\tEmpEmailId: " + this.getEmailId());
		System.out.println("\tEmpSalesForce: " + this.getSalesForceId());
	}

	public String getEmailId() {
		return emailId;
	}

	public String getEmpId() {
		return empId;
	}

	public String getName() {
		return name;
	}

	public String getSalesForceId() {
		return salesForceId;
	}

	public String getSalesForceId(String empId) {
		BasicEmployeeDetails tempSalesForceId = EmployeeMasterData.allEmployeeRecordMap.get(empId);
		if (tempSalesForceId != null && tempSalesForceId.getSalesForceId() != null)
			return tempSalesForceId.getSalesForceId();
		else
			return null;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId.toLowerCase();
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalesForceId(String salesForceId) {
		this.salesForceId = salesForceId;
	}

}
