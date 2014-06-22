
/**
 * @author Barros, Thiago Silva
 * 
 * Esta classe é responsável pelas funcionalidades do 
 * sistema, atualmente, a pintura das imagens na tela, 
 * rotação e atualização...
 * Posteriormente, esta classe sera responsavel  tambem
 * pelo retorno de tempo velocidade e carga d'agua
 * utilizada na ocorrencia. 
 *  
 */

package Application;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JPanel;


import DAO.emergenciaDAO;
import GUI.frmCCI;
import GUI.frmFront;
import GUI.frmMain;
import Model.AirPlane;
import Model.SimulatorTimer;
import Model.Water;
import Model.CCI;
import Model.Fire;
import Model.SimulatorMap;

/**
 * 
 * @author Barros, Thiago Silva
 * 
 * Classe Functions é um painel onde serão pintadas as imagens
 * Implementa ActionLister para ouvir as teclas de movimento, para o movimento automatico noa será necessario ActionListener
 * 
 */

public class Functions extends JPanel implements ActionListener, Observer{
	
	private SimulatorMap background;
	private CCI cci;
	private Fire fire;
	private Water agua;
	private AirPlane airplane;
	private Timer timer;
	private static boolean isAuto;
	private boolean run = false;
	private JLabel labeltimer;
	private emergenciaDAO dao;
	private static String statusMissao;
	
	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	private static Functions uniqueInstance;
	
	
	public static boolean isAuto() {
		return isAuto;
	}

	public static void setAuto(boolean Auto) {
		isAuto = Auto;
	}

	public static Functions getInstance(){
		//caso uniqueInstance seja nula recebe a intancia
		if(uniqueInstance == null){
			uniqueInstance = new Functions();
		}
		//retonrna a intancia unica
		return uniqueInstance;			
	}
	
	
	private Functions(){
		
		//mantem o foco na janela do simulador
		setFocusable(true);
		setDoubleBuffered(true);	
		//teclado é o ouvinte da classe
		addKeyListener(new TecladoAdapter()); 
		background = new SimulatorMap();
		
		dao = new emergenciaDAO();
		
		cci = new CCI();		
		cci.addObserver(dao);		
		cci.addObserver(this);
		cci.addObserver(SimulatorTimer.getInstance());
		
		fire = Fire.getInstance();		
		//adicionando a classe como observer de fire		
		fire.addObserver(dao);		
		fire.addObserver(this);		
		fire.addObserver(SimulatorTimer.getInstance());
		
		agua = new Water();
		
		airplane = new AirPlane();
		
		timer = new Timer(5,this);
				
		labeltimer = new JLabel("teste");
		labeltimer.setLocation(100, 500);
		labeltimer.setVisible(true);
		this.add(labeltimer);
		
		timer.start();
	}

	public void paint(Graphics g){
		
		/*Graphics 2d é a classe resposavel por manipular imagens
		essas intancia é como uma molduras, onde as imagens serão pintadas*/
		Graphics2D graficos = (Graphics2D) g;			

		//pinta a imagem de background na tela 
		graficos.drawImage(background.getImg(),0,0,null);
				
		/*como sera necessario rotacionar a imagem é necesário uma copia da moldura*/

		//Intancia responsavel por manter a imagem original
		AffineTransform origform = graficos.getTransform();
		//nova forma recebe uma copia  e esta sera rotacionada
        AffineTransform newform = (AffineTransform)(origform.clone());
        
        //apartir de uma instancia a moldura é rotacionada 
        newform.rotate(Math.toRadians(cci.getAngle()), cci.getX()+12, cci.getY()+17);
        
        //seta a nova moldura rotacionada
        graficos.setTransform(newform);
       
        //imagem é pintada na tela com os parametro passados apartir da moldura rotacionada
        if(cci.isVisible()){        	
        	graficos.drawImage(cci.getImagem(), (int)cci.getX(), (int)cci.getY(), this);
        }
        
        //agua recebe a posição do CCI e no eixo y recebe um valor FINAL a mais que significa a frente do CCI
        agua.setX((int) cci.getX()/*-17*/);
    	agua.setY((int) (cci.getY()/*+55*/));
    	
    	//se agua estiver visivel ela é pintada
    	if(agua.isVisible()){
    		//agua é pintada na moldura rotacionada
        	graficos.drawImage(agua.getImage(),(int)agua.getX(),(int)agua.getY(),this);
        }
        
        //volta a moldura para a posição original
        graficos.setTransform(origform);
        
        //pinta o fogo com a moldura padrao

        graficos.drawImage(airplane.getImgAirplane(),Fire.getX()-20,Fire.getY()+20,this);
        
        if(fire.isVisible()){
        	graficos.drawImage(fire.getImg(),Fire.getX(),Fire.getY(),this); 
        }
             
        //fecha o metodo de pintura
		g.dispose();		
	
	}
	
	//quando o keylistener é acionado
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (!isAuto && run == true){
				//fogo extinto
			if(!fire.isVisible()){
				
			}
			cci.move();//viatura é mexida 
			
			//checa colisões a cada movimento
			checkColisions();
			
			repaint();//metodo paint() é reexecutado	
		}else if(isAuto && run == true){
			autoMove();
		}
	}
	
	public void autoMove(){
		Fire.getInstance();
		
		
		if(isAuto ){
//			System.out.println(cci.getX()+" , "+cci.getY()+" , "+cci.getAngle());
			if (Fire.getX() != cci.getX()){
				
				if(Fire.getX() > cci.getX()){	
					// = 270
					if(cci.getAngle()!= 270){
						cci.setAngle(cci.getAngle()-0.5);
					}else{
						cci.setX(cci.getX()+1);
					}
					checkCollisionAuto();
				}else{
					// = 90					
					if(cci.getAngle()!= 90){
						cci.setAngle(cci.getAngle()+0.5);
					}else{
						cci.setX(cci.getX()-1);
					}
					checkCollisionAuto();
				}
				
			}else if (Fire.getY() != cci.getY()){
				
				if(Fire.getY() > cci.getY()){				
					//= 0
					if(cci.getAngle()!= 1){
						cci.setAngle(cci.getAngle()-0.5);
					}else{
						cci.setY(cci.getY()+1);
					}
					checkCollisionAuto();
				}else{
					//= 180
					if(cci.getAngle()!= 180){
						cci.setAngle(cci.getAngle()+0.5);
					}else{
						cci.setY(cci.getY()-1);
					}
					checkCollisionAuto();
				}			
					
			}
		}
	}
	
	
	
	private class TecladoAdapter extends KeyAdapter{
		
		//é passado como parametro a tecla pressionada
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(cci.isVisible()){
				agua.KeyPressed(arg0);		
			}
			cci.keyPressed(arg0);			
		}
		
		//é passado como parametro a tecla solta
		@Override
		public void keyReleased(KeyEvent arg0) {
			if (cci.isVisible()){
				agua.KeyRealeased(arg0);
			}
			cci.keyReleased(arg0);
		}	
	}
	
	public void checkColisions() {
		JLabel lblImageInfo;
		Rectangle rectangleCCI = cci.getBounds();
		Rectangle rectangleFire = fire.getBounds();
		Rectangle rectangleAgua = agua.getBounds();
		
		if (rectangleCCI.intersects(rectangleFire)){
	
			if(!agua.isVisible()){
				lblImageInfo = getImageInfo("res\\MsgSemAgua.png");
				JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1); 
											
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				cci.setVisible(false);
			
				agua.setVisible(false);
								
				cci.notificar("CCI Destruido");
			}else{
				if (rectangleAgua.intersects(rectangleFire)&&agua.isVisible()){
					lblImageInfo = getImageInfo("res\\MsgCanhaoAcionado.png");
					JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1);										
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					fire.setVisible(false);
					
					lblImageInfo = getImageInfo("res\\MsgCanhaoDesl.png");
					JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1);	
					agua.setVisible(false);
					
					
					//notificando observers	
					fire.notificar("Missão Cumprida");
					agua.setVisible(false);
				}
			}			
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
	
	public void checkCollisionAuto(){
		JLabel lblImageInfo;
		Rectangle rectangleCCI = cci.getBounds();
		Rectangle rectangleFire = fire.getBounds();
		
		if (rectangleCCI.intersects(rectangleFire)){
					
				agua.setVisible(true);
				lblImageInfo = getImageInfo("res\\MsgCanhaoAcionadoAuto.png");
				JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1);
				

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				fire.setVisible(false);
				lblImageInfo = getImageInfo("res\\MsgCanhaoDesl.png");
				JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1);	
				agua.setVisible(false);
				//notificando observers	
				fire.notificar("Missão Cumprida");
							
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		JLabel lblImageInfo;
		ArrayList<String> listaDataReturn = getEmergencyData();
		String dataReturn = "";
		
		frmFront.getInstance().dispose();
		if (arg1.equals("Missão Cumprida") && isAuto()){
			lblImageInfo = getImageInfo("res\\MsgMissaoCumpridaAuto.png");
			JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1);
		}else{
			if (arg1.equals("Missão Cumprida")){
				lblImageInfo = getImageInfo("res\\MsgMissaoCumpridaManual.png");
				JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1);
			}else{
				lblImageInfo = getImageInfo("res\\MsgCCIDestruido.png");
				JOptionPane.showMessageDialog(null,lblImageInfo , "Mensagem", -1);
			}
		}
		
		//ITERATOR		
		Iterator<String> iterator = listaDataReturn.iterator();
		while(iterator.hasNext()) {  
			   dataReturn += iterator.next();
			   dataReturn += "\n";
		} 

		JOptionPane.showMessageDialog(null, dataReturn, "Confirmação", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public static String getStatusMissao() {
		return statusMissao;
	}

	public static void setStatusMissao(String statusMissao) {
		Functions.statusMissao = statusMissao;
	}
	
	
	//ITERATOR
	
	public ArrayList<String> getEmergencyData() {
		ArrayList<String> data = new ArrayList<String>();
		
		String tempNameCCI;
		
		switch (CCI.getVTRnumber()) {
		case 1:
			tempNameCCI = "AP1-'Apollo'";
			break;
		case 2:
			tempNameCCI = "AP2-'Hercules'";
			break;
		case 3:
			tempNameCCI = "AP3-'Poseidon'";
			break;
		case 4:
			tempNameCCI = "AP4-'Zeus'";
			break;

		default:
			tempNameCCI = "Viatura de Bombeiro";
			break;
		}
		
		data.add("Dados da emergencia: ");		
		data.add("Nome da Viatura:  " +  tempNameCCI);
		data.add("Tempo de Emergencia:  " + SimulatorTimer.getTempo());
		data.add("Status da Emergencia:  " + Functions.getStatusMissao());		
		
		return data;
	}
	
}
