/**
 * @author Barros, Thiago Silva
 * 
 * Classe do Carro Contraincendio (CCI) 
 * Pode ser empregado padrão Singletone Strategy
 * por não necessitar de mais de uma
 * instancia
 * 
 * Tambem poderá ser empregado o metodo
 * template sendo que "Fire","Water","simulatorMap","CCI"
 * possui algo parecido
 */
package Model;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Observable;


import javax.swing.ImageIcon;

import GUI.frmCCI;
import GUI.frmMain;


public class CCI extends Observable{
	//atributos da posição do elemento
	private float x, y;
	//atributos de movimento do elemento
	private float dx, dy;	
	private static int VTRnumber; 
	private int width;
	private int height;
	private double angle,sAngle;
	//imagem do CCI
	private Image imagem;	
	private  boolean isVisible;
	
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public static int getVTRnumber() {
		return VTRnumber;
	}

	public static void setVTRnumber(int vTRnumber) {
		VTRnumber = vTRnumber;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	//atributos de rotação do elemnto
	public void setAngle(double angle) {
		if ((angle >= 360.0)  /*|| (angle <= -360.0)*/) {
			angle = 0;
		}	
		
		if ((angle < 0.0)  /*|| (angle <= -360.0)*/) {
			angle = 360;
		}	 
		this.angle = angle;
		
	}	

	public CCI(){
		//imagem atribuida esta em res\\CCI.gif
		
		String caminhocci;
		
		switch (VTRnumber) {
		
		case 1:
			caminhocci = "res\\AP1.gif";
			break;
		case 2:
			caminhocci = "res\\AP2.gif";
			break;
		case 3:
			caminhocci = "res\\AP3.gif";
			break;
		case 4:
			caminhocci = "res\\AP4.gif";
			break;

		default:
			
			caminhocci = "res\\CCI.gif";
			break;
		}
		
		
		
		ImageIcon reference = new ImageIcon(caminhocci);
		imagem = reference.getImage();
		
		//imagem é iniciada com as cordenadas x, y angle
		this.x = 870; 
		this.y = 385;
		this.angle = 90; 
		this.width = imagem.getWidth(null);
		this.height = imagem.getHeight(null);
		isVisible = true;
		
	}

	
	public void move(){
		//printados na tela os valores para auxilio na validação dos campos
		//auxiliará na implementação do Banco de dados
		//não permanecera na versão final do projeto		
		
		x += dx;
		y += dy;	
		angle+=sAngle;	
		
		//teste para soma do angle não passar de 360
		if ((angle >= 360.0)) {
			angle = 0;
		}	
		
		if ((angle < 0.0) ) {
			angle = 360;
		}
		
		if (x < 1){
			x = 1;
		}
		
		if (x > 890){
			x = 890;
		}
		
		if (y < 1){
			y = 1;
		}
		
		if (y > 385){
			y = 385;
		}
	}
	
	public void notificar(String result){
		frmMain.getInstance().disable();
		setChanged();
		notifyObservers(result);		
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,width,height);
	}
	
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public Image getImagem() {
		return imagem;
	}
		
	//movendo por teclado
	public void keyPressed (KeyEvent tecla){
		//cada tecla apertada, o CCI move
		int codigo = tecla.getKeyCode();
		
		if (codigo == KeyEvent.VK_UP){
			//dy = (float) 0.5;
			// 0 -90
			
			if((angle > 10 )&&(angle < 80 )){
				dy = (float) 0.5;
				dx = (float) -0.5;
			}
			
			//90 180
			if((angle > 100 )&&(angle < 170 )){
				dy = (float) -0.5;
				dx = (float) -0.5;
			}
			//180 270
			if((angle > 190 )&&(angle < 260 )){
				dy = (float) -0.5;
				dx = (float) 0.5;
			}
			//270 360
			if((angle > 280 )&&(angle < 350 )){
				dy = (float) 0.5;
				dx = (float) 0.5;
			}
			
			//movimenta pra baixo
			if((angle >= 0 )&&(angle <= 10 )){
				dy = (float) 0.5;
			}
			
			if((angle >= 350 )&&(angle <= 360 )){
				dy = (float) 0.5;
			}
			
			//movimenta pra cima
			if((angle >= 170 )&&(angle <= 180 )){
				dy = (float) -0.5;
			}
			
			if((angle > 180 )&&(angle <= 190 )){
				dy = (float) -0.5;
			}
			
			
			
			//movimenta pra esquerda
			if((angle >= 80 )&&(angle <= 90 )){
				dx = (float) -0.5;
			}
			
			if((angle > 90 )&&(angle <= 100 )){
				dx = (float) -0.5;
			}
			
			//movimenta pra direita
			if((angle >= 260 )&&(angle <= 270 )){
				dx = (float) 0.5;
			}
			
			if((angle > 270 )&&(angle <= 290 )){
				dx = (float) 0.5;
			}
		}
		
		if (codigo == KeyEvent.VK_DOWN){
//			dy = (float) 0.5;
			
			if((angle > 10 )&&(angle < 80 )){
				dy = (float) -0.5;
				dx = (float) 0.5;
			}
			
			//90 180
			if((angle > 100 )&&(angle < 170 )){
				dy = (float) 0.5;
				dx = (float) 0.5;
			}
			//180 270
			if((angle > 190 )&&(angle < 260 )){
				dy = (float) 0.5;
				dx = (float) -0.5;
			}
			//270 360
			if((angle > 280 )&&(angle < 350 )){
				dy = (float) -0.5;
				dx = (float) -0.5;
			}
			
			//movimenta pra baixo
			if((angle >= 0 )&&(angle <= 10 )){
				dy = (float) -0.5;
			}
			
			if((angle >= 350 )&&(angle <= 360 )){
				dy = (float) -0.5;
			}
			
			//movimenta pra cima
			if((angle >= 170 )&&(angle <= 180 )){
				dy = (float) 0.5;
			}
			
			if((angle > 180 )&&(angle <= 190 )){
				dy = (float) 0.5;
			}
			
			
			
			//movimenta pra esquerda
			if((angle >= 80 )&&(angle <= 90 )){
				dx = (float) 0.5;
			}
			
			if((angle > 90 )&&(angle <= 100 )){
				dx = (float) 0.5;
			}
			
			//movimenta pra direita
			if((angle >= 260 )&&(angle <= 270 )){
				dx = (float) -0.5;
			}
			
			if((angle > 270 )&&(angle <= 290 )){
				dx = (float) -0.5;
			}
		}
		
		if (codigo == KeyEvent.VK_LEFT){
			sAngle = (float) -0.3;
			
		}
		
		if (codigo == KeyEvent.VK_RIGHT){
			sAngle = (float) 0.3;			
		}
	}
	
	public void keyReleased(KeyEvent tecla){
		//cada tecla solta, o CCI para aonde esta
		int codigo = tecla.getKeyCode();
		
		if (codigo == KeyEvent.VK_UP){
			dy = 0;
			dx = 0;
			
		}
		
		if (codigo == KeyEvent.VK_DOWN){
			dy = 0;
			dx = 0;
		}

		if (codigo == KeyEvent.VK_LEFT){
			sAngle = 0;
			
		}	
		if (codigo == KeyEvent.VK_RIGHT){
			sAngle = 0;
		}	
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
