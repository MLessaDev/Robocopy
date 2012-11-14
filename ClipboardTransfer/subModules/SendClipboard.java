package subModules;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import javax.swing.JOptionPane;
import model.ClipMessage;
import model.Contact;

public class SendClipboard {

	private Contact contact;

	public SendClipboard (Contact contact){
		this.contact=contact;
	}	

	public void send(ClipMessage clip){		
		try {
			Socket s = new Socket(contact.getIp(), contact.getDoor());
			ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream()); 
			ObjectInputStream input = new ObjectInputStream(s.getInputStream()); 
			output.flush();	  
			output.writeObject(clip);   
			output.flush(); 
			boolean aceito = (Boolean)input.readObject();
			output.close();  
			s.close();
			if(aceito){
			JOptionPane.showMessageDialog(null, "Clipboard enviado para "+
					contact.getName()+" com sucesso!");
			}else{
				JOptionPane.showMessageDialog(null, "O Robocopy de "+
						contact.getName()+" parece estar desligado.\n" +
								"Talvez ele não queira ser incomodado...");
			}
		} catch (Exception e) {
			JOptionPane.showInternalMessageDialog(null, "Erro ao contactar o contato,\n" +
					"Verifique as configurações de IP e Porta");
		}  
	}

}
