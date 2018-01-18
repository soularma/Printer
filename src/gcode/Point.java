package gcode;

public class Point {


	public static final int X_DEFAUT=0,  Y_DEFAUT=0, Z_DEFAUT=0;

	private int x, y, z;
	
	public Point(int abs, int ord, int haut){
		setX(abs);
		setY(ord);
		setZ(haut);
	}

	public Point(){
		this(X_DEFAUT,Y_DEFAUT, Z_DEFAUT);
	}

	public int getX(){
		return this.x;
	}

	public void setX(int abs){
		this.x=abs;
	}

	public int getY(){
		return this.y;
	}

	public void setY(int ord){
		this.y=ord;
	}
	
	public int getZ(){
		return this.z;
	}
	public void setZ(int haut){
		this.z=haut;
	}

	public String afficheCoords(){
		return "(" + this.x + "," + this.y + "," + this.z +")"; 
	}

	public void déplacerVers(int abs,int ord, int haut){
		setX(abs);
		setY(ord);
		setZ(haut);
	}

	public void déplacerDe(int deltaX, int deltaY, int deltaZ){
		setX(this.x+deltaX);
		setY(this.y+deltaY);
		setZ(this.y+deltaZ);
	}
	
	public String toString(){
		return "(" + this.x + "," + this.y + "," + this.z + ")";  
	}

}
