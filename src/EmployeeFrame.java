import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class EmployeeFrame extends JFrame implements ActionListener,ItemListener{
	private static JFrame frame = null;
	private static JPanel panel = null;
	private static JTextField fNameTF = null;
	private static JTextField lNameTF = null;
	private static JTextField minSalTF = null;
	private static JTextField maxSalTF = null;
	private static JComboBox deptList = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			frame = new JFrame();
			frame.setSize(400,350);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			panel = new JPanel();
			frame.add(panel);
			panel.setLayout(null);
	
			/* *
        	 * Text label which represents the heading
        	 * */
        	JLabel heading = new JLabel("EMPLOYEE REPORT", SwingConstants.CENTER);
        	heading.setFont(new Font("Arial", Font.PLAIN, 20));
        	heading.setBounds(10,20,350,25);
        	panel.add(heading);        
        	
        	/* *
        	 * Text label and Text field which represents the first name
        	 * */        	
        	JLabel fNameLabel = new JLabel("First Name");
        	fNameLabel.setBounds(10,70,80,25);
        	panel.add(fNameLabel);
        	
        	fNameTF = new JTextField(50);
        	fNameTF.setBounds(100,70,165,25);
        	panel.add(fNameTF);
        	
        	/* *
        	 * Text label and Text field which represents the last name
        	 * */        	
        	JLabel lNameLabel = new JLabel("Last Name");
        	lNameLabel.setBounds(10,100,80,25);
        	panel.add(lNameLabel);
        	
        	lNameTF = new JTextField(50);
        	lNameTF.setBounds(100,100,165,25);
        	panel.add(lNameTF);
        	
        	/* *
        	 * Text label and Text field which represents the salary
        	 * */        	
        	JLabel minSalLabel = new JLabel("Min Salary");
        	minSalLabel.setBounds(10,130,80,25);
        	panel.add(minSalLabel);
        	
        	minSalTF = new JTextField(50);
        	minSalTF.setBounds(100,130,50,25);
        	panel.add(minSalTF);
        	minSalTF.setText("0");
        	JLabel maxSalLabel = new JLabel("Max Salary");
        	maxSalLabel.setBounds(200,130,80,25);
        	panel.add(maxSalLabel);
        	
        	maxSalTF = new JTextField(50);
        	maxSalTF.setBounds(280,130,50,25);
        	panel.add(maxSalTF);
        	maxSalTF.setText("0");
        	
        	/* *
        	 * Text label and Text field which represents the salary
        	 * */        	
        	JLabel deptLabel = new JLabel("Department");
        	deptLabel.setBounds(10,160,80,25);
        	panel.add(deptLabel);
        	
        	deptList = new JComboBox();
        	deptList.addItem("Choose one");
        	deptList.addItem("Marketing");
        	deptList.addItem("IT");
        	deptList.addItem("Sales");
        	deptList.setBounds(100,160,165,25);
        	/*deptList.addItemListener(new ItemListener() {
        		@Override
        		public void itemStateChanged(ItemEvent e) {
        			if(e.getStateChange()==ItemEvent.SELECTED) {
        				selectedDept = (String) deptList.getSelectedItem();
        			}
        		};
        	});*/
        	panel.add(deptList);
        	
        	
          	
        	/* *
        	 * Button which will be used to save the object in file
        	 * */
        	JButton generateBtn = new JButton("Generate Report");
        	generateBtn.setBounds(10,210,160,25);
        	generateBtn.addActionListener(new EmployeeFrame());
        	panel.add(generateBtn);
        	        	
        	frame.setVisible(true);
        	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			FileOutputStream fos = new FileOutputStream(new File("Keywords.bin"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			String fNameKW = fNameTF.getText();
			String lNameKW = lNameTF.getText();
			String minSalKW = minSalTF.getText();
			String maxSalKW = maxSalTF.getText();
			
			String selectedDept = (String) deptList.getSelectedItem();
		
			Keywords kw = new Keywords(fNameKW, lNameKW, Integer.parseInt(minSalKW),Integer.parseInt(maxSalKW), selectedDept);
			
			oos.writeObject(kw);
			fNameTF.setText("");
			lNameTF.setText("");
			minSalTF.setText("");
			maxSalTF.setText("");
			oos.close();
			fos.close();
			
			GetEmployeesFrame.read();
		}
		catch(FileNotFoundException event) {
			event.printStackTrace();
		}
		catch(IOException event) {
			event.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}
