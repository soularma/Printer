package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonXDroit extends JButton implements MouseListener{

	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/right.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/rightBLue.png");
	
	public Position position = new Position();
	
	public BoutonXDroit(int X) {
		this.position.setX(X);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	public BoutonXDroit() {
		this.position.setX(Position.POSITION_X_DEFAUT);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

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
