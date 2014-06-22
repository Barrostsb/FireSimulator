package Model;
import java.awt.Color;
import java.awt.Dimension;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.util.Observable;
import java.util.Observer;
   
import javax.swing.JLabel;  
import javax.swing.JPanel;  
import javax.swing.Timer;  

import Application.Functions;
import GUI.frmMain;
  
public class SimulatorTimer  extends JPanel implements Observer{  
    private static SimulatorTimer uniqueInstance;
	private JLabel lblTimer;  
	private JLabel lblMain;  
    private Timer timer;  
    static int minuto = 0, segundo=0;
    static String tempo; 
    
    
  
    public static String getTempo() {
		return tempo;
	}

	public static void setTempo(String tempo) {
		SimulatorTimer.tempo = tempo;
	}

	private SimulatorTimer() {  
		this.add(lblMain = new JLabel("TEMPO DE EMERGENCIA:"));
		lblMain.setBackground(Color.WHITE);
        this.add(this.getLabel());  
        this.go();  
    } 
    
	public static SimulatorTimer getInstance(){
		//caso uniqueInstance seja nula recebe a intancia
		if(uniqueInstance == null){
			uniqueInstance = new SimulatorTimer();
		}
		//retonrna a intancia unica
		return uniqueInstance;			
	}
  
    public JLabel getLabel() {  
        if (this.lblTimer == null) {  
        	this.lblTimer = new JLabel();
            this.lblTimer.setPreferredSize(new Dimension(100, 22));  
        }  
        return this.lblTimer;  
    }  
  
    public void go() {     	
    	
        ActionListener action = new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  

            	tempo = minuto + " : " + segundo;
            	
            	lblTimer.setText(tempo);
            	
            	
        		if( segundo >= 59 ){  
                    segundo = 0;  
                    minuto = minuto+1;
                    
                }else{
                	segundo ++;                	
                }        		                
            }              
        };  
        this.timer = new Timer(100, action);  
        this.timer.start();  
    }

    //Primeiro Observer a dar Update
	@Override
	public void update(Observable arg0, Object arg1) {
		Functions.setStatusMissao(arg1.toString());
		timer.stop();
		
	}   
}
