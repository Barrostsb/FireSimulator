package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Application.Functions;
import GUI.frmFront;
import Model.CCI;
import Model.Fire;
import Model.SimulatorTimer;

public class emergenciaDAO implements Observer {
	
	//atributos usados para a string de conexão
	private final String ADAPTER = "mysql";
	private final String HOST = "127.0.0.1";
	private final String PORT = "3306";
	private final String DATABASE = "fireSimulator";
	private final String USERNAME = "root";
	private final String PASSWORD = "root";
	//Conexão visivel no pacote
	private Connection connection;
	
		
	public emergenciaDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void insert(){
		
		try{
			open();
			PreparedStatement insere = connection.prepareStatement("INSERT INTO emergencia (controleViatura,data_emergencia,tempo,localizacaoX,localizacaoY,viaturaUtilizada,statusMissao) " +
																	"VALUES(?,?,?,?,?,?,?);");
			insere.setString(1, frmFront.getInstance().getControleVtr());
			 
			Date data = new Date(System.currentTimeMillis());
			
			insere.setObject(2, data);
			insere.setString(3, SimulatorTimer.getTempo());
			insere.setInt(4, Fire.getX());
			insere.setInt(5, Fire.getY());
			insere.setInt(6, CCI.getVTRnumber());
			insere.setString(7, Functions.getStatusMissao());
			insere.executeUpdate();
			//JOptionPane.showMessageDialog(null, "Emergencia cadastrada");
			close();
		}catch(SQLException s){
			System.err.println(s);
		}catch (Exception e) {
			System.err.println(e);
		}
	}


	public DefaultTableModel ListaEmergencia() throws Exception {  
		DefaultTableModel modeloTable;  
		
		modeloTable = new DefaultTableModel();  
		/* Set os nomes das colunas que eu quero na tabela */  
		
		modeloTable.setColumnIdentifiers(  
		new String[] { "IdEmerg","Status da Missão", "Nome da viatura","Controle da viatura","Data da Emergencia", "tempo de emergencia","Localização Eixo X da emergencia ","Localização Eixo X da emergencia"});  
		
		String query;
		PreparedStatement stm;
		ResultSet rs;  
		try{

			rs = ListaResultados();
			
		int posicao = 0;  
		  
		while (rs.next()) {  
		  
		modeloTable.insertRow(posicao,new Object[] {  
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getString(5),
				rs.getString(6),
				rs.getString(7),
				rs.getString(8)
				});  
		posicao++;  
		}  
		  
		} catch (SQLException e) {  
		e.printStackTrace();  
		throw new Exception("Erro ao Listar Emergencias");  
		}  
		return modeloTable;
		  
		  
		}

	public ResultSet ListaResultados() throws SQLException {
		String query;
		PreparedStatement stm;
		ResultSet rs;
		open();
		query = "SELECT emrg.idEmergencia, emrg.statusMissao, cci.NomeCCI, emrg.controleViatura, emrg.data_emergencia, emrg.tempo, emrg.localizacaoX,emrg.localizacaoY FROM emergencia emrg, cci cci WHERE viaturaUtilizada = idCCI ORDER BY emrg.idEmergencia ASC;";
		
		stm = connection.prepareStatement(query);
		
		rs = stm.executeQuery();
		return rs;
	}  
	
	
	public void delete(int id) {
		String query;
		PreparedStatement stm; 
		
		try{
			
			open();
			query = "DELETE FROM emergencia WHERE idEmergencia = ?;";
			
			stm = connection.prepareStatement(query);
			stm.setInt(1, id);
			
			stm.executeUpdate();
			close();
		}catch(SQLException s){
			System.err.println(s);
		}catch (Exception e) {
			System.err.println(e);
		}
		
	}
	
	public void open() throws SQLException {
		if (this.connection == null) {
			String connection = "jdbc:" + this.ADAPTER + "://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE;
			this.connection = DriverManager.getConnection(connection,this.USERNAME,this.PASSWORD);
		}
	}

	/**
	 * Método para fechar conexão com banco de dados
	 */
	public void close() throws SQLException {
		if (this.connection != null) {
			this.connection.close();
		}
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

	@Override
	public void update(Observable arg0, Object arg1) {
		JLabel lblImageInfo;
		int opcao = JOptionPane.showConfirmDialog(null,
				"Deseja gravar os dados da emergencia no banco?", 
				null, 
				JOptionPane.YES_NO_OPTION); 

		if (opcao == JOptionPane.YES_OPTION) { // SIM
			try {
				insert();									
				
				lblImageInfo = getImageInfo("res\\MsgGravarDados.png");
				JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1);
				System.exit(0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
		}else{
			System.exit(0);
		}
		
	}

}
