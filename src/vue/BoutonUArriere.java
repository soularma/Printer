package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonUArriere extends JButton implements MouseListener{


	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/rotationAxeULeft.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/rotationAxeULeftBlue.png");
	
	public Position position = new Position();
	
	public BoutonUArriere(int U) {
		this.position.setU(U);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	public BoutonUArriere() {
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
		this.position.setZ(coordonnee);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		this.setIcon(imgAppui);
		this.position.setU(this.position.getU() - 1);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.setIcon(img);
	}
	
	
	public void setOrigine() {
		this.position.setOrigine();
	}

}
