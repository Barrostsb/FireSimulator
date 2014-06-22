/**
 * @author Barros, Thiago Silva
 * 
 * Formulario responsavel gerar ocorrencia
 * aqui serão passados parametros de onde foi
 * a ocorrencia
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Functions;
import Model.Fire;


public class frmWarningGenerate extends FormTemplate{

	private static frmWarningGenerate uniqueInstance = new frmWarningGenerate();
	
	private JButton btnOk;
	private JButton btnCancelar;
	private JButton btnLimpar;
	private JButton btnRandom;
	private JLabel lblMain;
	private JTextField txtEixoX;
	private JTextField txtEixoY;
	
	
	private frmWarningGenerate(){
		super("Simulador de Emergencia - Gerar Emergencia");
		Container mainCont = getContentPane();
		
		Container southCont = new JPanel();
		Container northCont = new JPanel();
		Container centerCont = new JPanel();
		
		FlowLayout flow = new FlowLayout();
		GridLayout grid = new GridLayout(2,1);
		
		Container dataConteiner = new JPanel();
		Container dataConteiner2 = new JPanel();
		
		dataConteiner.setLayout(grid);	
		dataConteiner2.setLayout(grid);
		
		dataConteiner.add(new JLabel("Eixo X: "));
		dataConteiner.add(txtEixoX = new JTextField(15));
		dataConteiner.add(new JLabel("Eixo Y: "));
		dataConteiner.add(txtEixoY = new JTextField(15));
		dataConteiner2.add(btnRandom = new JButton("Gerar posiçoes aleatórias")); 
		
		
		southCont.setLayout(flow);
		
		northCont.add(lblMain = new JLabel("Gerar Emergencia"));
		lblMain.setFont(new Font("Arial", Font.BOLD + Font.ITALIC ,20 ));
		
		centerCont.add(dataConteiner);
		centerCont.add(dataConteiner2);
		
		southCont.add(btnCancelar = new JButton("cancelar"));
		southCont.add(btnOk = new JButton("Ok"));
		southCont.add(btnLimpar = new JButton("Limpar"));
		
		mainCont.add(BorderLayout.NORTH, northCont);
		mainCont.add(BorderLayout.SOUTH, southCont);
		mainCont.add(BorderLayout.CENTER, centerCont);
		
		
		/**
		 * Padrão Método Template
		 */		
	
		//chamada do Método template
		runForm(400, 200, false);		
		
		
		btnOk.addActionListener(new btnOkHandler());
		btnCancelar.addActionListener(new btnCancelarHandler());
		btnLimpar.addActionListener(new btnLimparHandler());
		btnRandom.addActionListener(new btnRandomHandler());	
		
	}
	
	public static frmWarningGenerate getInstance(){
		
		if(uniqueInstance == null){
			uniqueInstance = new frmWarningGenerate();
		}
		
		return uniqueInstance;
	}
	
	private class btnOkHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frmCCI.getInstance().setVisible(true);
			try{
				Fire.getInstance().setX(Integer.parseInt(txtEixoX.getText()));
				Fire.getInstance().setY(Integer.parseInt(txtEixoY.getText()));				
			}catch (Exception e){
				
			}
			setVisible(false);			
		}
		
	}
	
	private class btnCancelarHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frmFront.getInstance().setVisible(true);
			dispose();			
		}		
	}
	
	private class btnLimparHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			txtEixoX.setText("");
			txtEixoY.setText("");			
		}
		
	}
	
	private class btnRandomHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Random randonNum = new Random();
			
			txtEixoX.setText(Integer.toString(randonNum.nextInt(890)));
			txtEixoY.setText(Integer.toString(randonNum.nextInt(385)));			
			
		}
		
	}
}
