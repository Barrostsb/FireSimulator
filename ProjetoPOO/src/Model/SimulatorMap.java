/**
 * @author Barros, Thiago Silva
 * 
 * Classe SimulatorMap
 * 
 * classe responsavel pelo plano de fundo 
 * do simulador
 * 
 */
package Model;

import java.awt.Image;

import javax.swing.ImageIcon;

public class SimulatorMap {
	//imagem do plano de fundo
	private Image img;
	
	public SimulatorMap(){
		//imagem atribuida esta em res\\fundo.gif
		ImageIcon reference = new ImageIcon("res\\background.png");
		img = reference.getImage();		
	}
	
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

}
