package util;
import java.net.InetAddress;
import java.sql.*;

import javax.swing.JOptionPane;

import model.Contact;

public class DatabaseAccess {

	private static Connection con = null;
	private static String ip;

	public DatabaseAccess () {
		connect();
	}

	private void connect(){
		String url = "jdbc:mysql://"+ip+":3306/robocopy";
		String user = "root";
		String pass = "13579";
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,user,pass);
		}
		catch (ClassNotFoundException e){
			JOptionPane.showMessageDialog(null,"Classe não encontrada");
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null,"Problemas com o BD"+e);
		}
	}

	public void executeCommand(String sql) {
		try {
			Statement stm = con.createStatement();
			stm.execute(sql);        
		} catch(SQLException e) {
			System.out.print("Erro ao executar Query!");
			e.printStackTrace();
		}   
	}

	public ResultSet ExecuteQuery(String sql) {
		try {
			Statement stm = con.createStatement();  
			return stm.executeQuery(sql); 
		} catch(SQLException e) {
			System.out.print("Erro ao executar Query!");
			e.printStackTrace();
			return null;
		} 
	}

	public Contact getThisUser(){
		try {
			Statement stm = con.createStatement();  
			//Implementar forma de pegar ip atual
			ResultSet rs =stm.executeQuery("SELECT * FROM Contacts WHERE ip='127.000.000.001'");
			while (rs.next()){
				Contact contact = new Contact();
				contact.setDoor(rs.getInt("door"));
				contact.setIp(rs.getString("ip"));
				contact.setName(rs.getString("name"));
				contact.setOnline(true);
				logon(contact);
				return contact;
			}

		} catch(SQLException e) {
			System.out.print("Erro ao executar Query!");
			e.printStackTrace();
			return null;
		}
		return null; 
	}

	public void AddUser(String nome){
		try {
			Statement stm = con.createStatement();
			//Implementar forma de pegar ip atual
			stm.execute("INSERT INTO Contacts (name,ip,door,online) VALUES('"+nome+"','127.000.000.001',7020,1)");
		} catch(SQLException e) {
			System.out.print("Erro ao executar Query!");
			e.printStackTrace();
		}   
	}
	public void logoff(Contact user){
		try {
			Statement stm = con.createStatement();
			//Implementar forma de pegar ip atual
			stm.execute("UPDATE Contacts SET online=0 WHERE ip='127.000.000.001'");
		} catch(SQLException e) {
			System.out.print("Erro ao executar Query!");
			e.printStackTrace();
		}  
	}

	public void logon(Contact user){
		try {
			Statement stm = con.createStatement();
			//Implementar forma de pegar ip atual
			stm.execute("UPDATE Contacts SET online=1 WHERE ip='127.000.000.001'");
		} catch(SQLException e) {
			System.out.print("Erro ao executar Query!");
			e.printStackTrace();
		}  
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		DatabaseAccess.ip = ip;
	}

	public static String localIP()
	{
		String s="";
		try
		{
			InetAddress in = InetAddress.getLocalHost();
			s = in.getHostAddress();

		} catch(Exception j){}

		return s;
	}

}

