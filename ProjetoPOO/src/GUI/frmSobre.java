package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class frmSobre extends FormTemplate {

	private JButton btnVoltar;
	private JLabel lblMain;
	private static frmSobre uniqueInstance = new frmSobre();
	
	private frmSobre() {
		super("Obrigado por Usar o Fire Simulator 11-2013");
		
		Container mainCont = getContentPane();
		Container southCont = new JPanel();
		Container northCont = new JPanel();
		Container centerCont = new JPanel();
	
		FlowLayout flow = new FlowLayout();
		southCont.setLayout(flow);
		centerCont.setLayout(flow);
		
		northCont.add(lblMain = new JLabel("FIRE SIMULATOR 11-2013"));
		lblMain.setFont(new Font("Serif", Font.BOLD + Font.ITALIC ,20 ));
		
		southCont.add(btnVoltar = new JButton("Voltar"));
		
		ImageIcon imgLogo = new ImageIcon("res\\sobre.png");
		centerCont.add(BorderLayout.NORTH , new JLabel(imgLogo));	
		
		mainCont.add(BorderLayout.SOUTH, southCont);
		mainCont.add(BorderLayout.CENTER, centerCont);
		mainCont.add(BorderLayout.NORTH, northCont);
		
		
		
		/**
		 * Padrão Método Template
		 */		
	
		//chamada do metodo template
		runForm(600, 430, false);
		
		btnVoltar.addActionListener(new btnVoltarHandler());
	}
	
	public static frmSobre getInstance(){
		
		if(uniqueInstance == null){
			uniqueInstance = new frmSobre();
		}
		
		return uniqueInstance;
	}
	
	private class btnVoltarHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frmFront.getInstance().setVisible(true);
			dispose();
			
		}
	}
	

}
