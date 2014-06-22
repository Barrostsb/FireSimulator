/**
 * 
 * @author ThiMary
 * 
 * Classe Water
 * pode ser feito uma inteface agente extintor e 
 * implementar agua e espuma para padroes de projeto
 *
 */
package Model;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class Water {
	private Image image;
	private int x,y;
	private int width;
	private int heigth;
	private double angle;
	private boolean isVisible;
	
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

	public void setAngle(double angle) {
		this.angle = angle;
	}

	CCI cci = new CCI();
	
	//parametros serão baseados na posição do CCI
	
	public Water(){		
		ImageIcon reference = new ImageIcon("res\\water.gif");
		image = reference.getImage();
		isVisible = false;
		
		
		this.x = 100;
		this.y = 100;
		
		width = image.getWidth(null);
		heigth = image.getHeight(null);
	
	}
	
	public Rectangle getBounds(){
//		int recX = 0,recY= 0;
//		
//////		if((angle > 10 )&&(angle < 80 )){
//////			dy = (float) 0.5;
//////			dx = (float) -0.5;
//////		}
//////		
//////		//90 180
//		if((cci.getAngle() > 75 )&&(cci.getAngle() < 115 )){
//			
//			recX = x+30;
//			recY = y-120;
//		}
////		
//////		//180 270
//		if((cci.getAngle() > 190 )&&(cci.getAngle() < 260 )){
//			recX = x-30;
//			recY = y-120;
//		}
////		//270 360
//		if((angle > 255 )&&(angle < 285 )){
//			retangulo = new Rectangle(x-30,y-120/*x+17,y-120*/,width,heigth);
//		}
//		
//		return retangulo;
	return new Rectangle(x+35,y-100,width,heigth);
	//return new Rectangle(x+30,y-120/*x+17,y-120*/,width,heigth);
	}

	public double getAngle() {
		return angle;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x-17;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y+55;
	}

	//checa a visibilidade do elemento
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public void KeyPressed(KeyEvent tecla){
		int codigo = tecla.getKeyCode();
		
		//barra de espaço é apertada elemento se torna visivel
		if (codigo == KeyEvent.VK_SPACE){
			isVisible = true;
		}		
	}
	
	public void KeyRealeased(KeyEvent tecla){
		int codigo = tecla.getKeyCode();
		
		//barra de espaço é solta elemento se torna invisivel
		if (codigo == KeyEvent.VK_SPACE){
			isVisible = false;
		}		
	}
}
