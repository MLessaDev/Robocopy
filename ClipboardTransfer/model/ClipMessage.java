package model;

import java.io.Serializable;

public class ClipMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Contact contact;
	private String text;
	
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}	
}
