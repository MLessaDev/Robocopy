package util;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


public class TextTransfer{
	
	public static void setStringToClipboard(String str) {
		Transferable transferableText = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferableText, null);
    }
	
      public static String getStringFromClipboard() {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                                              .getContents(null);
 
        try {
            if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) t.getTransferData(DataFlavor.stringFlavor);
                return text;
            }
        }
        catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
 
        return null;
    }
  }

