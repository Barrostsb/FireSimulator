/**
 * @author Barros, Thiago Silva
 * 
 * Formulario inicial do projeto
 * Nele será escolhido pelo usuário
 * se o controle da viatura será
 * manual ou automático
 * 
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import Application.Functions;
import Model.Relatorio;

public class frmFront extends FormTemplate{
	
	private static frmFront uniqueInstance = new frmFront();
	
	private JButton btnManualControl;
	private JButton btnAutoControl;
	private JButton btnsimulacoes;
	private JButton btnRelatorio;
	private JButton btnSobre;
	private JLabel lblMain;
	private String controleVtr;
	
	
	public String getControleVtr() {
		return controleVtr;
	}

	public void setControleVtr(String controleVtr) {
		this.controleVtr = controleVtr;
	}

	private frmFront(){		
		super("Fire Simulator");	
		
		Container mainCont = getContentPane();
		Container southCont = new JPanel();
		Container northCont = new JPanel();
		Container centerCont = new JPanel();
		
		FlowLayout flow = new FlowLayout();
		southCont.setLayout(flow);
		centerCont.setLayout(flow);
		
		northCont.add(lblMain = new JLabel("FIRE SIMULATOR"));
		lblMain.setFont(new Font("Serif", Font.BOLD + Font.ITALIC ,20 ));
		
			
		southCont.add(btnRelatorio = new JButton("Relatorio"));
		southCont.add(btnsimulacoes = new JButton("Ver Simulações"));
		southCont.add(btnSobre = new JButton("?"));
		
		centerCont.add(btnAutoControl = new JButton("Controle automático"));
		centerCont.add(btnManualControl = new JButton("controle manual"));
				
		ImageIcon imgLogo = new ImageIcon("res\\logoFireSimulator.png");
		centerCont.add(BorderLayout.NORTH , new JLabel(imgLogo));		
		
		mainCont.add(BorderLayout.NORTH, northCont);
		mainCont.add(BorderLayout.SOUTH, southCont);
		mainCont.add(BorderLayout.CENTER, centerCont);
		
		/**
		 * Padrão Método Template
		 */		
	
		//chamada do metodo template
		runForm(300, 370 , true);
		
		//adicionando ouvintes aos botoes
		btnManualControl.addActionListener(new btnManualControlHandler());
		btnAutoControl.addActionListener(new btnAutoControlHandler());
		btnsimulacoes.addActionListener(new btnSimulacoesHandler());
		btnRelatorio.addActionListener(new btnRelatorioHandler());
		btnSobre.addActionListener(new btnSobreHandler());
	}
	
	public static frmFront getInstance(){
		
		if(uniqueInstance == null){
			uniqueInstance = new frmFront();
		}
		
		return uniqueInstance;
	}
		
	/**
	 * @author Barros,Thiago Silva
	 *
	 * Classe privada, que controlará as ações 
	 * sobre o botão btnManualControl 
	 * 
	 */
	
	private class btnManualControlHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frmWarningGenerate.getInstance().setVisible(true);
			setControleVtr("Controle Manual");
			Functions.setAuto(false);
			setVisible(false);	
		}		
	}
	
	/**
	 * @author Barros,Thiago Silva
	 *
	 * Classe privada, que controlará as ações 
	 * sobre o botão btnAutoControl 
	 * 
	 */
	
	private class btnAutoControlHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frmWarningGenerate.getInstance().setVisible(true);
			setVisible(false);		
			setControleVtr("Controle Automático");
			Functions.setAuto(true);
		}		
	}
	
	private class btnSimulacoesHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frmListaEmergencia.getInstance().setVisible(true);
			setVisible(false);			
		}		
	}
	private class btnSobreHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			frmSobre.getInstance().setVisible(true);
			setVisible(false);			
		}		
	}
	private class btnRelatorioHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				new Relatorio();
			} catch (Exception e) {
				// TODO: handle exception
			}			
		}		
	}
}
