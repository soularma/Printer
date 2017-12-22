package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonYHaut extends JButton implements MouseListener{


	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/ZDown.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/ZDownBlue.png");
	
	public Position position = new Position();
	
	public BoutonYHaut(int Y) {
		position.setY(Y);
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
		return this.position.getY();
	}

	public void setPosBouton(int coordonnee) {
		this.position.setY(coordonnee);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		this.setIcon(imgAppui);
		this.setPosBouton(this.getPosBouton() + 1);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.setIcon(img);
	}

}
