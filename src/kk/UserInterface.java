package kk;


import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager.LookAndFeelInfo;

public class UserInterface {
	
	private JPanel panel;
	private JPanel panelFrame;
	public JFrame loginFrame;
	private JTextField userTextField;
	private JTextField passwordTextField;
	private JButton registerButton;
	private JButton loginButton;
	private SQLiteConnector connector;
	private String userName;
	private String password;
	private JLabel errorLabel;
	
	private JTextField idTextField;
	private JTextField nameTextField;
	private JPasswordField enterPasswordField;
	private JPasswordField confirmPasswordField;
	
	Font fn;
	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface window = new UserInterface();
					window.loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UserInterface() throws FontFormatException, IOException {
		connector = new SQLiteConnector();
		//connector.createTable();
		//connector.deleteUser();
		initialize();
	}
	
	private void sendErrorMsg (String message) {
		errorLabel.setText(message);
		errorLabel.setFont(fn);
		Timer time = new Timer (3000, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				errorLabel.setVisible(false);
				errorLabel.setText("");
				errorLabel.setVisible(true);
			}
		});
		time.setRepeats(false);
		time.start();
	}

	private void initialize() throws FontFormatException, IOException {
		fn = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Aquib\\Downloads\\kalpurush.ttf"));
		fn = fn.deriveFont(Font.BOLD, 15);
		loginFrame = new JFrame("অপারেটিং সিস্টেম");
		loginFrame.setBackground(UIManager.getColor("Button.focus"));
		loginFrame.setBounds(100, 100, 550, 400);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setForeground(new Color(0, 51, 51));
		panel.setBackground(Color.LIGHT_GRAY);
		
		
		//buttonIcon = ImageIO.read(new File("G:\\Eclipse\\Project\\src\\login_button.png"));
		loginButton = new JButton("লগ ইন");
		loginButton.setFont(fn);
		loginButton.setForeground(UIManager.getColor("Table.dropLineColor"));
		//loginButton.setBackground(new Color(0, 51, 51));
		loginButton.setBounds(140, 237, 119, 25);
		loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		panel.setLayout(null);
		panel.add(loginButton);
		
		//buttonIcon = ImageIO.read(new File("G:\\Eclipse\\Project\\src\\register.png"));
		registerButton = new JButton("রেজিস্টার");
		registerButton.setFont(fn);
		//registerButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		registerButton.setForeground(Color.BLACK);
		//registerButton.setBackground(new Color(0, 51, 51));
		registerButton.setBounds(281, 237, 119, 25);
		registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(registerButton);
		
		userTextField = new JTextField();
		userTextField.setBounds(286, 107, 150, 25);
		userTextField.setFont(fn);
		panel.add(userTextField);
		userTextField.setColumns(10);
		userTextField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(286, 152, 150, 25);
		passwordTextField.setFont(fn);
		panel.add(passwordTextField);
		passwordTextField.setColumns(10);
		passwordTextField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		
		JLabel userLabel = new JLabel("ইউজার নাম");
		userLabel.setFont(fn);
		userLabel.setForeground(Color.BLACK);
		userLabel.setBackground(UIManager.getColor("ToggleButton.select"));
		userLabel.setBounds(138, 109, 70, 15);
		panel.add(userLabel);
		
		JLabel passwordLabel = new JLabel("পাসওয়ার্ড");
		passwordLabel.setFont(fn);
		passwordLabel.setForeground(Color.BLACK);
		passwordLabel.setBounds(140, 154, 119, 15);
		panel.add(passwordLabel);
		
		errorLabel = new JLabel("");
		errorLabel.setFont(fn);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setForeground(new Color(255, 0, 0));
		errorLabel.setBounds(150, 30, 250, 20);
		panel.add(errorLabel);
		
		loginFrame.getContentPane().add(panel);
		
		
		registerButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				userName = userTextField.getText();
				password = passwordTextField.getText();
				/*if (userName.length() == 0 || password.length() == 0){
					sendErrorMsg ("Fill up all required fields");
				}
				else if (connector.existUser(userName)) {
					sendErrorMsg ("User already exists");
				}
				else {
					connector.createUser(userName, password);
					sendErrorMsg("User created successfully");
				}*/
				panel.setVisible(false);
				try {
					registrationInitialize();
				} catch (FontFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		panel.getRootPane().setDefaultButton(loginButton);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userName = userTextField.getText();
				password = passwordTextField.getText();
				
				if (userName.length() == 0 || password.length() == 0){
					sendErrorMsg ("সম্পূর্ণ রুপে পূরণ করুন");
				}
				else if (!connector.existUser(userName)) {
					sendErrorMsg ("দুঃখিত।এই নামে ইউজার নেই");
				}
				else if (connector.validUser(userName, password)) {
					sendErrorMsg ("সফল্ভাবে লগ ইন হয়েছে");
					connector.close();
					FileFrameNew fileFrame = null;
					try {
						fileFrame = new FileFrameNew(userName);
					} catch (FontFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					loginFrame.setVisible(false);
					fileFrame.fileFrame.setVisible(true);
					//fileInitialize(userName);
				}
				else {
					sendErrorMsg ("পাসওয়ার্ড ভুল হয়েছে");
				}
			}
		});
		loginFrame.setResizable(false);
	}
	
	private void registrationInitialize() throws FontFormatException, IOException{
		fn = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Aquib\\Downloads\\kalpurush.ttf"));
		fn = fn.deriveFont(Font.BOLD, 15);
		panelFrame = new JPanel();
		panelFrame.setForeground(new Color(0, 51, 51));
		panelFrame.setBackground(Color.LIGHT_GRAY);
		panelFrame.setLayout(null);
		
		idTextField = new JTextField();
		idTextField.setBounds(305, 119, 150, 25);
		idTextField.setFont(fn);
		panelFrame.add(idTextField);
		idTextField.setColumns(10);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(305, 72, 150, 25);
		nameTextField.setFont(fn);
		panelFrame.add(nameTextField);
		
		enterPasswordField = new JPasswordField();
		enterPasswordField.setBounds(305, 163, 150, 25);
		enterPasswordField.setFont(fn);
		panelFrame.add(enterPasswordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(305, 213, 150, 25);
		confirmPasswordField.setFont(fn);
		panelFrame.add(confirmPasswordField);
		
		JLabel nameLabel = new JLabel("পূর্ণ নাম");
		nameLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		nameLabel.setBounds(40, 72, 200, 15);
		panelFrame.add(nameLabel);
		nameLabel.setFont(fn);
		nameLabel.setForeground(Color.BLACK);
		
		JLabel idLabel = new JLabel("ইউজার আইডি");
		idLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		idLabel.setBounds(40, 121, 200, 15);
		panelFrame.add(idLabel);
		idLabel.setFont(fn);
		idLabel.setForeground(Color.BLACK);
		
		JLabel password1Label = new JLabel("পাসওয়ার্ড");
		password1Label.setHorizontalAlignment(SwingConstants.TRAILING);
		password1Label.setBounds(40, 165, 200, 15);
		panelFrame.add(password1Label);
		password1Label.setFont(fn);
		password1Label.setForeground(Color.BLACK);
		
		JLabel password2Label = new JLabel("পাসওয়ার্ড নিশ্চিত করুন");
		password2Label.setHorizontalAlignment(SwingConstants.TRAILING);
		password2Label.setBounds(40, 215, 200, 15);
		panelFrame.add(password2Label);
		password2Label.setFont(fn);
		password2Label.setForeground(Color.BLACK);
		
		JButton submitButton = new JButton("সম্মত হন");
		submitButton.setFont(fn);
		submitButton.setBounds(292, 263, 117, 25);
		panelFrame.add(submitButton);
		
		JButton backButton = new JButton("ফিরে যান");
		backButton.setFont(fn);
		backButton.setBounds(132, 263, 117, 25);
		panelFrame.add(backButton);
		
		panelFrame.add(errorLabel);
		
		loginFrame.getContentPane().add(panelFrame);
		panelFrame.getRootPane().setDefaultButton(submitButton);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userName = idTextField.getText();
				password = enterPasswordField.getText();
				String temp = confirmPasswordField.getText();
				
				if (userName.length() == 0 || password.length() == 0){
					sendErrorMsg ("সম্পূর্ণ ভাবে পূরণ করুন");
				}
				else if (!temp.equals(password)) {
					sendErrorMsg ("দুঃখিত। পাসওয়ার্ড মিলে নাই");
				}
				else if (connector.existUser(userName)) {
					sendErrorMsg ("দুঃখিত।এই নাম এ ইউজার বিদ্যমান");
				}
				else {
					try {
						connector.createUser(userName, password);
					} catch (Exception err) {
						err.printStackTrace();
					}
					File fileName = new File("E:\\"+userName);
					if (!fileName.exists()) fileName.mkdir();
					panel.setVisible(true);
					panelFrame.setVisible(false);
					loginFrame.setVisible(false);
					loginFrame.setVisible(true);
					sendErrorMsg("সফল্ভাবে ইউজার তৈরি হয়েছে");
				}
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
				panelFrame.setVisible(false);
				loginFrame.setVisible(false);
				loginFrame.setVisible(true);
				panel.getRootPane().setDefaultButton(loginButton);
			}
		});
	}
	
	
}
