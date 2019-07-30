public class Assistant extends Employee
{
	private String employeeID;
	private String employeeName;
	
	Assistant(String employeeID,String employeeName)
	{
		this.employeeID=employeeID;
		this.employeeName=employeeName;
	}
	
	public String getEmployeeID()
	{
		return employeeID;
	}
	public String getEmployeeName()
	{
		return employeeName;
	}
	public String toString()
	{
		return "Welcome "+employeeName+"!";
	}
}