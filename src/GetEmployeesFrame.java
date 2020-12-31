import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class GetEmployeesFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static JFrame _reportFrame = null;
	private static JPanel _reportPanel = null;
	private static JTextArea textArea  = null;
	private static List<Employee> sortedEmployees;
	public static void read() {
		try {

			FileInputStream fis = new FileInputStream(new File("Keywords.bin"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Keywords kw = (Keywords) ois.readObject();		
			
			String fNameKW = kw.getFNameKW();
			String lNameKW = kw.getLNameKW();
			String deptKW = kw.getDeptKW();
			int minKW = kw.getMinSalKW();
			int maxKW = kw.getMaxSalKW();
			
			ois.close();
			fis.close();
			
			_reportFrame = new JFrame();	
			_reportFrame.setSize(410,810);
			_reportFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			_reportPanel = new JPanel();
			_reportFrame.add(_reportPanel);
			_reportPanel.setLayout(null);
			

			ProcessingEmployees process = new ProcessingEmployees();
			sortedEmployees = process.generateReport(fNameKW,lNameKW,deptKW,minKW,maxKW);
			sortedEmployees.forEach(System.out::println);
						
			if(fNameKW.contains("")&&lNameKW.contains("")&&deptKW.contains("Choose one")&&minKW == 0&&maxKW==0) {
				setHeader("General Salary Report By Departments");
				generalReport();
			}
			else {
				setHeader("Search Result");
				reportByCriteria();
			}
			_reportFrame.add(_reportPanel);
			_reportFrame.setVisible(true);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setHeader(String header) {
    	JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
    	headerLabel.setFont(new Font("Arial", Font.PLAIN, 15));
    	headerLabel.setBounds(10,20,350,25);
    	_reportPanel.add(headerLabel);   
    	
	}
	public static void generalReport() {
		textArea = new JTextArea();
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		Map<String, Long>empCountByDept = getEmpCountByDept();
		//empCountByDept.forEach((dept,count)->System.out.printf("%s has %d employees", dept,count));
		
		Map<String, Double>empSumByDept = GetEmpSumByDept();
		//empSumByDept.forEach((dept,sum)->System.out.printf("%s has %f sum", dept,sum));
		
		Map<String, Double>empAvgByDept = GetEmpAvgByDept();
		//empAvgByDept.forEach((dept,avg)->System.out.printf("%s has %f avg", dept,avg));
		
		Long count = 0L;
		String curDept = "";
		for(int i = 0; i < sortedEmployees.size(); i++) {
			curDept = sortedEmployees.get(i).getDept();
			Long noOfEmpInDept =empCountByDept.get(curDept); 
			if(count == 0) {
				textArea.append(curDept.toUpperCase()+"\n");
				textArea.append(String.format("%-12s%-12s%-12s %-12s\n","First Name","Last Name", "Salary", "Department"));
			}
			textArea.append(sortedEmployees.get(i).toString()+"\n");
			count++;
			if(count==noOfEmpInDept) {
				String sumByDept = getCurrencyFormat(empSumByDept.get(curDept));
				String avgByDept = getCurrencyFormat(empAvgByDept.get(curDept));
				textArea.append("Summary\n");
				textArea.append("Number of Employees: "+ noOfEmpInDept+"\n");
				textArea.append("Average salary of an employee: "+avgByDept+"\n");
				textArea.append("Total budget for salary: "+sumByDept+"\n\n");
				count = 0L;
			}
		}
		textArea.append("COMPANY XXX\n");
		textArea.append("Total Number of Employees: "+sortedEmployees.size()+".\n");
		Double sum = sortedEmployees.stream().mapToDouble(Employee::getSalary).sum();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(sum);
		textArea.append("Total Budget for Salaries: "+moneyString+".\n");
		
		Double average = sortedEmployees.stream()
				.mapToDouble(Employee::getSalary)
				.average()
				.getAsDouble();
		textArea.append("Average Salary of an Employee: "+getCurrencyFormat(average)+".\n");
		textArea.setBounds(20,50,350,700);

		_reportPanel.add(textArea);
	}
	
	static public void reportByCriteria() {
		textArea = new JTextArea();
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		Map<String, Long>empCountByDept = getEmpCountByDept();
		
		Map<String, Double>empSumByDept = GetEmpSumByDept();
		
		Map<String, Double>empAvgByDept = GetEmpAvgByDept();
		
		Long count = 0L;
		String curDept = "";
		for(int i = 0; i < sortedEmployees.size(); i++) {
			curDept = sortedEmployees.get(i).getDept();
			Long noOfEmpInDept =empCountByDept.get(curDept); 
			if(count == 0) {
				textArea.append(curDept.toUpperCase()+"\n");
				textArea.append(String.format("%-12s%-12s%-12s %-12s\n","First Name","Last Name", "Salary", "Department"));
			}
			textArea.append(sortedEmployees.get(i).toString()+"\n");
			count++;
			if(count==noOfEmpInDept) {
				String sumByDept = getCurrencyFormat(empSumByDept.get(curDept));
				String avgByDept = getCurrencyFormat(empAvgByDept.get(curDept));
				textArea.append("Number of Matches: "+ noOfEmpInDept+"\n\n");
				count = 0L;
			}
		}
		textArea.setBounds(20,50,350,700);
		_reportPanel.add(textArea);
	}
	
	static public Map<String, Long>getEmpCountByDept(){
		Map<String, Long>result = sortedEmployees.stream()
				.collect(Collectors.groupingBy(Employee::getDept,TreeMap::new,Collectors.counting()));
		return result;
	}
	
	static public Map<String, Double>GetEmpSumByDept(){
		Map<String,Double>result = sortedEmployees.stream()
				.collect(Collectors.groupingBy(Employee::getDept, TreeMap::new, Collectors.summingDouble(Employee::getSalary)));
		return result;
	}
	
	static public Map<String, Double>GetEmpAvgByDept(){
		Map<String,Double>result = sortedEmployees.stream()
				.collect(Collectors.groupingBy(Employee::getDept, TreeMap::new, Collectors.averagingDouble(Employee::getSalary)));
		return result;
	}
	
	static public String getCurrencyFormat(double salary) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(salary);
		return moneyString;
	}
}
