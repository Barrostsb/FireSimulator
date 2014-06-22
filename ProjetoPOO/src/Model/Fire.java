/**
 * @author Barros, Thiago Silva
 * 
 * Classe do Fire 
 * Pode ser empregado padrão Strategy
 * 
 */
package Model;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Observable;

import javax.swing.ImageIcon;

import GUI.frmMain;


public class Fire extends Observable{
	//atributos da posição do elemento
	private static int x,y;
	private static Fire uniqueInstance;
	private int width;
	private int heigth;	
	//imagem do fogo
	private Image img;
	//atributo de visibilidade será usado quando o fogo for extinto
	private boolean isVisible;
	
	public static Fire getInstance(){
		//caso uniqueInstance seja nula recebe a intancia
		if(uniqueInstance == null){
			uniqueInstance = new Fire();
		}
		//retonrna a intancia unica
		return uniqueInstance;			
	}

	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	private Fire(){
		//imagem atribuida esta em res\\fire.gif
		ImageIcon reference = new ImageIcon("res\\fire.gif");
		img = reference.getImage();
		
		//imagem é iniciada com as cordenadas x, y e visibilidade true
		//coordenadas boas pra teste!
		x = 500;
		y = 50;
		isVisible = true;
		
		width = img.getWidth(null);
		heigth = img.getHeight(null);
		
	/*	TODO posição aleatoria do fogo
	    numero randomico
	    Random rand = new Random();
		this.x = rand.nextInt();
		this.y = rand.nextInt();*/
		
	}
	
	//TODO tratar colisão de elementos	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,heigth);
	}
	
	public void notificar(String result){
		frmMain.getInstance().disable();
		setChanged();
		notifyObservers(result);		
	}

	public static int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public static int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
