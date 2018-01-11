package vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class BoutonUSB extends JButton implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/usb.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/usbRed.png");
	
	public BoutonUSB() {
		this.setIcon(img);
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		actionUSB();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.setIcon(imgAppui);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.setIcon(img);
	}
	
	
	public void actionUSB() {
		JFrame treeView = new JFrame(); 
		treeView.setTitle("Select GCode File :");	
		treeView.setSize(400, 600);
		
		
		
		
		treeView.setVisible(true);
		
	}
	
};
