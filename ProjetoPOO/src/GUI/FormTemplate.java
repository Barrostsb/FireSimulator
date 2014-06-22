package GUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public abstract class FormTemplate extends JFrame{
	private ImageIcon imgIcon;
	
	public FormTemplate(String string) {
		super (string);
		imgIcon = new ImageIcon("res\\icon.png");
	}

	final void runForm(int width, int height, boolean arg2){
		
		setSize(width, height);		
		setIconImage(imgIcon.getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		if (CloseOperationDefaultIsExit(arg2)){
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}else{
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}	
	
	// Método gancho
	boolean	CloseOperationDefaultIsExit(boolean arg0){
		return	arg0;
	}
}
