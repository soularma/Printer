package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonYBas extends JButton implements MouseListener{

	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/down.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/downBlue.png");
	
	public Position position = new Position();
	
	public BoutonYBas(int Y) {
		this.position.setX(Y);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	public BoutonYBas() {
		this.position.setY(Position.POSITION_Y_DEFAUT);
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
		this.setPosBouton(this.getPosBouton() - 1);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.setIcon(img);
	}

}
