package GUI;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Application.Functions;
import Model.CCI;

public class frmCCI extends FormTemplate {

		private JButton btnVoltar;
		private JButton btnVtr1;
		private JButton btnVtr2;
		private JButton btnVtr3;
		private JButton btnVtr4;
		private JLabel lblMain;	
		
		private static frmCCI uniqueInstance = new frmCCI();
		
		private frmCCI(){		
			super("Escolher Viatura");	
			
			Container mainCont = getContentPane();
			Container southCont = new JPanel();
			Container northCont = new JPanel();
			Container centerCont = new JPanel();
			
			FlowLayout flow = new FlowLayout();
			GridLayout grid = new GridLayout(2, 2);
			southCont.setLayout(flow);
			centerCont.setLayout(grid);
			
			northCont.add (lblMain = new JLabel("Escolha a viatura"));
			lblMain.setFont(new Font("Arial", Font.BOLD + Font.ITALIC ,20 ));
			
			southCont.add(btnVoltar = new JButton("Voltar"));
			
			ImageIcon vtr1 = new ImageIcon("res\\AP1.gif");
			ImageIcon vtr2 = new ImageIcon("res\\AP2.gif");
			ImageIcon vtr3 = new ImageIcon("res\\AP3.gif");
			ImageIcon vtr4 = new ImageIcon("res\\AP4.gif");

			centerCont.add(btnVtr1 = new JButton("    AP1",vtr1));
			centerCont.add(btnVtr2 = new JButton("    AP2",vtr2));
			centerCont.add(btnVtr3 = new JButton("    AP3",vtr3));
			centerCont.add(btnVtr4 = new JButton("    AP4",vtr4));
			
			mainCont.add(BorderLayout.NORTH, northCont);
			mainCont.add(BorderLayout.SOUTH, southCont);
			mainCont.add(BorderLayout.CENTER, centerCont);
			
			/**
			 * Padrão Método Template
			 */		
		
			//chamada do metodo template
			runForm(500, 300, false);
			
			btnVoltar.addActionListener(new btnVoltarHandler());
			
			
			btnVtr1.addActionListener(new btnChangeCciHandler(1));
			btnVtr2.addActionListener(new btnChangeCciHandler(2));
			btnVtr3.addActionListener(new btnChangeCciHandler(3));
			btnVtr4.addActionListener(new btnChangeCciHandler(4));
		}
		
		public static frmCCI getInstance(){
			//caso uniqueInstance seja nula recebe a intancia
			if(uniqueInstance == null){
				uniqueInstance = new frmCCI();
			}
			//retonrna a intancia unica
			return uniqueInstance;			
		}
		
		private class btnVoltarHandler implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frmFront.getInstance().setVisible(true);
				dispose();				
			}			
		}
		
		private class btnChangeCciHandler implements ActionListener{
			private int vtrNum;
			
			public btnChangeCciHandler(int num) {
				this.vtrNum = num;
			}
			
			public void tips(){

				JLabel lblImageInfo;
				if(Functions.isAuto()){
					/**
					 * Padrão Decorator
					 */
					lblImageInfo = getImageInfo("res\\TipsManualControl.png");
					JOptionPane.showMessageDialog(null,lblImageInfo , "Dicas", -1);  
				}else{
					lblImageInfo = getImageInfo("res\\TipsAutoControl.png");
					JOptionPane.showMessageDialog(null,lblImageInfo , "Dicas", -1);  
				}
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CCI.setVTRnumber(vtrNum);
				tips();
				frmMain.getInstance();
				setVisible(false);
				Functions.getInstance();
				Functions.getInstance().setVisible(true);
				Functions.getInstance().setRun(true);
			}
			
			public JLabel getImageInfo(String caminho){
				ImageIcon imgImageInfo;
				JLabel lblImageInfo;
				
				/**
				 * Padrão Decorator
				 */
				
				imgImageInfo = new ImageIcon(caminho);  
				lblImageInfo = new JLabel(imgImageInfo);
				return lblImageInfo;
			}
		}	
}
