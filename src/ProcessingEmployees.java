import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class ProcessingEmployees {
	private static List<Employee> _employees;
	private static Scanner input = null;
	public ProcessingEmployees() {
		this.openFile("Employees.txt");
		this.readFile();
		this.closeFile();
	}
	
	public static void openFile(String filename) {
		try {
			input = new Scanner(Paths.get(filename));	
		}
		catch(IOException ioException) {
			System.err.println("Error opening file " +filename+". Terminating");
			System.exit(1);
		}	
	}
	
	public static void readFile() {
		try {
			/*int counter = 0;
			Scanner countLns = new Scanner(Paths.get("Employees.txt"));	
			while(countLns.hasNextLine()) {
				String lineExtracted = countLns.nextLine();
				String[] tokens = lineExtracted.split(",");
				counter++;
			}
			//_employees = new Employee[counter];
			//counter = 0;*/
			_employees = new ArrayList<Employee>();
			while(input.hasNextLine()) {
				String lineExtracted = input.nextLine();
				String[] tokens = lineExtracted.split(",");
				Employee newEmp = new Employee(tokens[0],tokens[1],Double.parseDouble(tokens[2]),tokens[3]);
				_employees.add(newEmp);
			}
			
			
		}
		catch(NoSuchElementException elementException) {
			System.err.println("File improperly formed. Terminating. ");
			System.exit(1);
		}
		catch(IllegalStateException illegalStateException) {
			System.err.println("Error reading from file. Terminating. ");
			System.exit(1);
		}
		
	}
	
	public static void closeFile() {
		if(input!=null) {
			input.close();
		}
	}
	
	static Function<Employee, String>byFirstName = Employee::getFName;
	static Function<Employee, String>byLastName = Employee::getLName;
	static Function<Employee, Double>bySalary = Employee::getSalary;
	static Function<Employee, String>byDept = Employee::getDept;
	
	static Comparator<Employee>compareByFullName = Comparator.comparing(byLastName).thenComparing(byFirstName);
	static Comparator<Employee>compareByDeptSalaryName = Comparator.comparing(byDept)
			.thenComparing(bySalary)
			.thenComparing(compareByFullName);
	
	
	public static List<Employee>generateReport(String fNameKW,String lNameKW,String deptKW,int minKW,int maxKW){
		List<Employee>sortedEmp = _employees.stream()
				.filter(e->{
					if(fNameKW.equals("")|| e.getFName().equals(fNameKW)) {
						return true;
					}
					return false;
				})
				.filter(e->{
					if(lNameKW.equals("")|| e.getLName().equals(lNameKW)) {
						return true;
					}
					return false;
				})
				.filter(e->{
					if(deptKW.equals("Choose one")|| e.getDept().equals(deptKW)) {
						return true;
					}
					return false;
				})
				.filter(e->{
					if(minKW==0&&maxKW==0) {
						return true;
					}
					else if(minKW==0 && maxKW!=0) {
						return e.getSalary()<=(double)maxKW;
					}
					else if(maxKW==0&& minKW!=0) {
						return e.getSalary()>=(double)minKW;
					}
					else if(minKW!=0&& maxKW!=0) {
						return e.getSalary()>=minKW&&e.getSalary()<=maxKW;
					}
					return false;
				})
				.sorted(compareByDeptSalaryName)
				.collect(Collectors.toList());
		return sortedEmp;
	};
	static Predicate<Employee>fourToSixThousand = 
			e->(e.getSalary()>=4000&& e.getSalary()<=6000);
}
