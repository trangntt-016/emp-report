import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.NumberFormat;
import java.util.*;
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
			_reportFrame.setSize(410,610);
			_reportFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			_reportPanel = new JPanel();
			_reportFrame.add(_reportPanel);
			_reportPanel.setLayout(null);
			

			ProcessingEmployees process = new ProcessingEmployees();
			sortedEmployees = process.generateReport(fNameKW,lNameKW,deptKW,minKW,maxKW);
			sortedEmployees.forEach(System.out::println);
			
			setHeader("Report on Employees");
			
			displayAfterSort();
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
	public static void displayAfterSort() {
		JTextArea textArea = new JTextArea();
		textArea.append(String.format("%-16s %-16s %-16s %-16s\n\n","First Name","Last Name", "Salary", "Department"));
		for(Employee emp:sortedEmployees) {
			textArea.append(emp.toString());
			textArea.append("\n\n");
		}
		Double sum = sortedEmployees.stream().mapToDouble(Employee::getSalary).sum();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(sum);
		textArea.append("Sum of Employees's Salaries: "+moneyString+"\n\n");
		
		Double average = sortedEmployees.stream()
				.mapToDouble(Employee::getSalary)
				.average()
				.getAsDouble();
		formatter = NumberFormat.getNumberInstance();
		String avgString = formatter.format(average);
		textArea.append("Average of Employees's Salaries: "+avgString);
		textArea.setBounds(20,50,350,500);

	_reportPanel.add(textArea);
	}
	

}
