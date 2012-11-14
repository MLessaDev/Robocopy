package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;


import robocopy.actions.*;
import subModules.LogContacts;
import subModules.SendClipboard;

import util.TextTransfer;

import model.ClipMessage;
import model.Contact;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private SendClipboard client;
	private MainForm frame;

	public void run(){
		frame = new MainForm();
		frame.setVisible(true);
	}

	public MainForm() {
		setResizable(false);
		setTitle("RoboCopy");
		setBounds(100, 100, 354, 160);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 349, 125);
		getContentPane().add(panel);

		JLabel label = new JLabel("Escolha o contato pra mandar seu clipboard");
		label.setBounds(12, 12, 331, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("Contato:");
		label_1.setBounds(12, 49, 70, 15);
		panel.add(label_1);

		final JComboBox cmbContact = new JComboBox();
		cmbContact.setBounds(99, 44, 244, 25);
		panel.add(cmbContact);

		LogContacts logContacts = new LogContacts(cmbContact);
		logContacts.start();
		
		JButton btnSend = new JButton("Enviar clipboard");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client = new SendClipboard((Contact)cmbContact.getSelectedItem());
				ClipMessage clip = new ClipMessage();
				clip.setText(TextTransfer.getStringFromClipboard());
				clip.setContact(Action.getUser());
				client.send(clip); 
			}
		});
		btnSend.setBounds(171, 93, 166, 25);
		panel.add(btnSend);

		final JCheckBox chkOnOff = new JCheckBox("Ligado");
		chkOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Action.getServer().setAwake(chkOnOff.isSelected());
			}
		});
		chkOnOff.setBounds(12, 89, 129, 23);
		panel.add(chkOnOff);
		chkOnOff.setSelected(Action.getServer().isAwake());
	}

	public MainForm getFrame() {
		return frame;
	}

	public void setFrame(MainForm frame) {
		this.frame = frame;
	}
	
	
}
