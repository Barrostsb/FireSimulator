package Model;

import java.awt.Image;

import javax.swing.ImageIcon;

public class AirPlane {
	private float x, y;
	private Image imgAirplane;
	
	
	public Image getImgAirplane() {
		return imgAirplane;
	}
	public void setImgAirplane(Image imgAirplane) {
		this.imgAirplane = imgAirplane;
	}
	public AirPlane() {
		ImageIcon reference = new ImageIcon("res\\AirPlane.png");
		imgAirplane = reference.getImage();
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
