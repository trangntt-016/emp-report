import java.text.NumberFormat;
import java.util.*;

public class Employee {
	private String _fName;
	private String _lName;
	private double _salary;
	private String _department;
	
	public Employee(String firstName, String lastName, double salary, String department) {
		this._fName = firstName;
		this._lName = lastName;
		this._salary = salary;
		this._department = department;
	};
	
	public String getFName() {
		return _fName;
	}
	public String getName() {
		return String.format("%s %s",getFName(), getLName());
	}
	
	public String getLName() {
		return _lName;
	}
	
	
	public double getSalary() {
		return _salary;
	}
	
	public String getDept() {
		return _department;
	}
	
	public String getCurrencyFormat(double salary) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(salary);
		return moneyString;
	}
	@Override
	public String toString() {
		return String.format("%-20s %-20s %-20s %-20s", getFName(), getLName(), getCurrencyFormat(getSalary()), getDept());
	}
	
}
