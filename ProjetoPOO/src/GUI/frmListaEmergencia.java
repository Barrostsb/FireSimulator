package GUI;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import DAO.emergenciaDAO;
import Model.Relatorio;

public class frmListaEmergencia extends FormTemplate{
	
	private static frmListaEmergencia uniqueInstance = new frmListaEmergencia();
	
	private JButton btnVoltar;
	private JButton btnRelatorio;
	private JButton btnDelEmergencia;
	private JLabel lblMain;
	private emergenciaDAO emergenciaDAO;
	private JTable table;
	private JScrollPane scrollPane;
	private Container c;
	
	
	private frmListaEmergencia(){
		
		super("Visualizar Emergencia");	
		
		emergenciaDAO = new emergenciaDAO();
		
		
		Container mainCont = getContentPane();
		Container southCont = new JPanel();
		Container northCont = new JPanel();
		Container centerCont = new JPanel();
	
		FlowLayout flow = new FlowLayout();
		southCont.setLayout(flow);
		centerCont.setLayout(flow);
	
		northCont.add (lblMain = new JLabel("Emergencias Cadastradas"));
		lblMain.setFont(new Font("Arial", Font.BOLD + Font.ITALIC ,20 ));
		southCont.add (btnVoltar = new JButton("Voltar"));
		southCont.add (btnRelatorio = new JButton("Gerar Relatorio"));
		
		
		mainCont.add(BorderLayout.SOUTH, southCont);
		mainCont.add(BorderLayout.NORTH, northCont);
		

		// Alterando possibilidade de reorganização das colunas e edição de campos
		try {
			table = new JTable(emergenciaDAO.ListaEmergencia()){
				//não sera possivel editar as celulas
				public boolean isCellEditable(int row, int column) {                
					return false;  
				};	
			};
			//não será possuivel reoganizar as colunas
			table.getTableHeader().setReorderingAllowed(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);
		table.setPreferredScrollableViewportSize(new Dimension(800,300));
		
		
		centerCont.add (scrollPane);
		centerCont.add (btnDelEmergencia = new JButton("Deletar Emergencia"));
		mainCont.add(BorderLayout.CENTER, centerCont);
		
		/**
		 * Padrão Método Template
		 */		
	
		//chamada do metodo template
		runForm(900, 500, false);
		
		btnVoltar.addActionListener(new btnVoltarHandler());
		btnDelEmergencia.addActionListener(new btnDeletarHandler());
		btnRelatorio.addActionListener(new btnRelatorioHandler());

	}
	
	public static frmListaEmergencia getInstance(){
		
		if(uniqueInstance == null){
			uniqueInstance = new frmListaEmergencia();
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
	
	
	private class btnDeletarHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int id = 0;

			try {
				// retorna o valor da primeira coluna da linha selecionada
				id = Integer.valueOf((String) table.getValueAt(table.getSelectedRow(), 0));
				System.out.println(id);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Nenhuma emergencia selecionada!");
				System.out.println(id);
				
				return;
			}

			int opcao = JOptionPane.showConfirmDialog(null,
					"Deseja realmente remover esta emergencia?", 
					null, 
					JOptionPane.YES_NO_OPTION); 

			if (opcao == JOptionPane.YES_OPTION) { // SIM
				try {
					
					// remove usuario
					emergenciaDAO.delete(id);

					// popula a JTable - Refresh											
					dispose();

					JOptionPane.showMessageDialog(null, "Emergencia removida com sucesso!");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}					
			} else { // NAO 
				return;
			}
			new frmListaEmergencia();
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
