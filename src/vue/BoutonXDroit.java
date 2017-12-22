package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonXDroit extends JButton implements MouseListener{

	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/left.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/leftBlue.png");
	
	public Position position = new Position();
	
	public BoutonXDroit(int X) {
		position.setX(X);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	public int getPosBouton() {
		return this.position.getX();
	}

	public void setPosBouton(int coordonnee) {
		this.position.setZ(coordonnee);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		this.setIcon(imgAppui);
		this.position.setX(this.position.getX() + 1);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.setIcon(img);
	}

}
