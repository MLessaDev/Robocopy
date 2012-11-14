package subModules;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import util.TextTransfer;
import model.ClipMessage;

public class ReciveClipboard extends Thread {

	private int porta;
	private boolean timeout;
	private boolean awake=true;

	public ReciveClipboard (int porta){
		this.porta=porta;
		this.awake=true;
	}

	public void run() {
		ServerSocket server = null;
		Socket s = null;
		try {
			server = new ServerSocket(porta);
			server.setSoTimeout(250);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			try {
				sleep(250);
				s=server.accept();
				if(s!=null){
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					output.writeObject(false);
				}
			} catch (Exception e1) {}
			while (awake) {
				timeout = false;
				try {
					try {
						s = server.accept();
					} catch (Exception e) {
						timeout = true;
					}
					if (!timeout) {
						ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
						output.writeObject(true);
						ObjectInputStream input = new ObjectInputStream(s.getInputStream());  
						ClipMessage clip = (ClipMessage)input.readObject();	
						int resp = JOptionPane
								.showConfirmDialog(null, "Seu parceiro(a) "
										+ clip.getContact().getName() + "\n "
										+ "lhe enviou um clip. Deseja Aceitar?");
						if (resp == 0) {
							TextTransfer.setStringToClipboard(clip.getText());
						}
						input.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public boolean isAwake() {
		return awake;
	}

	public void setAwake(boolean awake) {
		this.awake = awake;
	}	

}
