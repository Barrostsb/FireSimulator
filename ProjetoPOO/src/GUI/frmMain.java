/**
 * @author Barros, Thiago Silva
 * 
 * Formulario responsavel por receber  
 * todo o ambiente grafico de simulação 
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import Application.Functions;
import Model.SimulatorTimer;


public class frmMain extends FormTemplate {

	private static frmMain uniqueInstance = new frmMain();

	private frmMain(){		
		super("Simulador de ocorrencias");

		Container layout = getContentPane();
		Container northContainer = new JPanel();
		
		
		BorderLayout border = new BorderLayout();
		northContainer.setLayout(border);

		//inicia as funções do sistema, como a pintura de todos os componentes na tela
		
		layout.add(BorderLayout.NORTH, northContainer);
		layout.add(BorderLayout.CENTER, Functions.getInstance());
		layout.add(BorderLayout.SOUTH, SimulatorTimer.getInstance());
		
		
		/**
		 * Padrão Método Template
		 */		
	
		//chamada do metodo template
		runForm(920, 550, true);
		
	}

	public static frmMain getInstance(){
		//caso uniqueInstance seja nula recebe a intancia
		if(uniqueInstance == null){
			uniqueInstance = new frmMain();
		}
		//retonrna a intancia unica
		return uniqueInstance;			
	}

}
