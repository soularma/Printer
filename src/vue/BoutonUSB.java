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
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import vue.explorateur.ExplorateurFichiers;
	

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
		System.out.println("Changement d'icone pour bouton USB");
		ExplorateurFichiers explorateurFichier = new ExplorateurFichiers("/"); 
		explorateurFichier.getSelectedFile();
	}

};
