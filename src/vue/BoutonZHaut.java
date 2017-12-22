package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonZHaut extends JButton implements MouseListener{


	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/ZUp.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/ZUpBlue.png");
	
	public Position position = new Position();
	
	public BoutonZHaut(int Z) {
		this.position.setZ(Z);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	public BoutonZHaut() {
		this.position.setZ(Position.POSITION_Z_DEFAUT);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	public void setPosBouton(int coordonnee) {
		this.position.setZ(coordonnee);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		this.setIcon(imgAppui);
		this.position.setZ(this.position.getZ() + 1);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.setIcon(img);
	}

}
