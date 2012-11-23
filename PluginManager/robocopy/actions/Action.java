package robocopy.actions;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import model.Contact;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import subModules.ReciveClipboard;
import util.DatabaseAccess;
import view.MainForm;



public class Action implements IWorkbenchWindowActionDelegate {
	private static Contact user;
	private static ReciveClipboard server;
	private DatabaseAccess db;
	private boolean active=false;
	private MainForm form;

	public Action() {
	
	}

	public void run(IAction action) {
		if(!active){
			String ip=null;
			try {
				BufferedReader in = new BufferedReader(new FileReader(".server.dat"));
				while (in.ready()) {
					ip = in.readLine();
				}
				in.close();
			} catch (IOException e) {
			}
			if(ip==null){
				ip=JOptionPane.showInputDialog("Digite o endereço ip do servidor.");
				while (!validate(ip)){
					JOptionPane.showMessageDialog(null, "Endereço ip inválido. Tente novamente");
					ip=JOptionPane.showInputDialog("Digite o endereço ip do servidor.");
				}
				File arquivo = new File(".server.dat");
				try {
					PrintStream print = new PrintStream(arquivo);
					print.print(ip);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		

			DatabaseAccess.setIp(ip);
			db = new DatabaseAccess();	
			user = db.getThisUser();
			if (user==null){
				String nome =JOptionPane.showInputDialog("Digite seu nome para começar a usar o Robocopy!");
				while (nome==null || nome.trim().length()<1){
					JOptionPane.showMessageDialog(null, "Nome inválido, tente novamente");
					nome =JOptionPane.showInputDialog("Digite seu nome para começar a usar o Robocopy!");
				}
				db.AddUser(nome);
			}
			server = new ReciveClipboard(user.getDoor());
			server.start();
			form = new MainForm();
			form.run();
			active=true;
		}else{
			form.getFrame().setVisible(true);
		}
	}
	public static boolean validate(String ip){          
		String PATTERN = 
				"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();             
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
		db.logoff(user);
	}
	public void init(IWorkbenchWindow window) {

	}

	public static Contact getUser() {
		return user;
	}

	public static void setUser(Contact user) {
		Action.user = user;
	}

	public static ReciveClipboard getServer() {
		return server;
	}

	public static void setServer(ReciveClipboard server) {
		Action.server = server;
	}

}