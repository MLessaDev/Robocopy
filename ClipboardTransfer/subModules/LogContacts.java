package subModules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;

import util.DatabaseAccess;

import model.Contact;

public class LogContacts extends Thread {
	private ArrayList<Contact> contactList = new ArrayList<Contact>();
	private DatabaseAccess db;
	private JComboBox cmb;

	public LogContacts(JComboBox cmb){
		this.cmb=cmb;
	}

	public void run(){
		db = new DatabaseAccess();
		while (true){
			contactList.clear();
			String query = "SELECT * FROM Contacts WHERE online=1";//AND ip <> '"+robocopy.actions.Action.getUser().getIp()+"'";
			ResultSet rs = db.ExecuteQuery(query);
			Contact contact;
			try {
				while (rs.next()) {
					contact = new Contact();
					contact.setDoor(rs.getInt("door"));
					contact.setIp(rs.getString("ip"));                  
					contact.setName(rs.getString("name"));
					contact.setOnline(true);
					contactList.add(contact);
				}
				try {
					comboLoad();
					Thread.sleep(10000);
				} catch (Exception e) {

				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}					
		}
	}	

	private void comboLoad(){
		if (!contactList.isEmpty()){
			int aux = cmb.getSelectedIndex();
			cmb.removeAllItems();
			for(Contact contact : contactList){
				cmb.addItem(contact);
			}
			cmb.setSelectedIndex(aux);
		}
	}


	public ArrayList<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}


}
