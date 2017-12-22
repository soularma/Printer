package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonUAvant extends JButton implements MouseListener{


	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/rotationAxeURight.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/rotationAxeURightBlue.png");
	
	public Position position = new Position();
	
	public BoutonUAvant(int U) {
		this.position.setU(U);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	public BoutonUAvant() {
		this.position.setU(Position.POSITION_Z_DEFAUT);
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
		this.position.setU(coordonnee);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		this.setIcon(imgAppui);
		this.position.setU(this.position.getU() + 1);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.setIcon(img);
	}

}
