import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import Players.Player;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Login {

	private static JFrame frmBlackjackGame;
	private JPasswordField passwordField;
	public static Connection connection = null;
	public static String player;
	private JTextField userNametxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmBlackjackGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		connection = sqliteConnection.dbConnector();
		initialize();
	}
	public static int signUp(String userName,String password)
	{
		String query  = "select * from BlackJackT  where username=?";
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, userName);
			ResultSet rs = pst.executeQuery();
			int count=0;
			while(rs.next())
			{
				count++;
			}
			if(count==1)
				JOptionPane.showMessageDialog(null, "Duplicate Username! choose different Username");
			if(password.equals(""))
				JOptionPane.showMessageDialog(null, "Please enter a Password");
			else
			{
				query = "insert into BlackJackT (username,password,points) values (?,?,100)";
				pst = connection.prepareStatement(query);
				pst.setString(1, userName);
				pst.setString(2, password);
				pst.execute();
				pst.close();
				JOptionPane.showMessageDialog(null, "You have signed up succefully! \nEnjoy!!");
				player = userName;
				@SuppressWarnings("unused")
				AppWindow window = new AppWindow(player);
				frmBlackjackGame.setVisible(false);
			}
			return 1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBlackjackGame = new JFrame();
		frmBlackjackGame.setTitle("BlackJack Game");
		frmBlackjackGame.setBounds(100, 100, 450, 300);
		frmBlackjackGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBlackjackGame.getContentPane().setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(158, 136, 99, 20);
		frmBlackjackGame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(68, 67, 67, 14);
		frmBlackjackGame.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(68, 139, 67, 14);
		frmBlackjackGame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					int count=0;
					boolean correctPassword = false;
					String query = "select * from BlackJackT where username=? and password=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, userNametxt.getText());
					pst.setString(2, passwordField.getText());
					ResultSet rs = pst.executeQuery();
					count=0;
					while(rs.next())
					{
						count++;
					}
						if(count==1)
						{
							JOptionPane.showMessageDialog(null, "Username and Password are correct!");
							correctPassword=true;
						}
						else if(count>1)
						{
							JOptionPane.showMessageDialog(null, "Duplicate username and password!");
						}
						else
							JOptionPane.showMessageDialog(null, "Username and password incorrect!");
				
					rs.close();
					pst.close();
					if(correctPassword)
					{
						player = userNametxt.getText();
						@SuppressWarnings("unused")
						AppWindow window = new AppWindow(player);
						frmBlackjackGame.setVisible(false);
					}
					
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e.toString());
				
				}
					
			}
		});
		btnLogin.setBounds(68, 194, 89, 23);
		frmBlackjackGame.getContentPane().add(btnLogin);
		
		JButton btnNewButton = new JButton("Sign Up");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				signUp(userNametxt.getText(), passwordField.getText());
				}
		});
		btnNewButton.setBounds(272, 194, 89, 23);
		frmBlackjackGame.getContentPane().add(btnNewButton);
		
		userNametxt = new JTextField();
		userNametxt.setBounds(158, 64, 99, 20);
		frmBlackjackGame.getContentPane().add(userNametxt);
		userNametxt.setColumns(10);
		
		JLabel lblWelcome = new JLabel("Welcome To Our Game of BlackJack!");
		lblWelcome.setBounds(121, 14, 240, 42);
		frmBlackjackGame.getContentPane().add(lblWelcome);
	}
}
