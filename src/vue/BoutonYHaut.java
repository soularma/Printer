package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonYHaut extends JButton implements MouseListener{


	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/up.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/upBlue.png");
	
	public Position position = new Position();
	
	public BoutonYHaut(int Y) {
		this.position.setY(Y);
		this.setIcon(img);
		this.addMouseListener(this);
	}
	
	public BoutonYHaut() {
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

	@Override
	public void mousePressed(MouseEvent event) {
		this.setIcon(imgAppui);
		this.position.setY(this.position.getY() + 1);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.setIcon(img);
	}

}
