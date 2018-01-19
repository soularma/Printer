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
import vue.FenetrePrincipale;

public class BoutonUSB extends JButton implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private ImageIcon img = new ImageIcon("Icons/usb.png");
	private ImageIcon imgAppui = new ImageIcon("Icons/usbRed.png");
	private ExplorateurFichiers explorateurFichier;
	public FenetrePrincipale fenetrePrincipale;
	private String path;
	private boolean appui;

	

	public BoutonUSB(boolean appui) {
		this.setIcon(img);
		this.addMouseListener(this);
		this.appui=appui;
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
		appui=true;
		System.out.println("Changement d'icone pour bouton USB");
		 explorateurFichier = new ExplorateurFichiers("/");
		 //System.out.println("-- boutonUSB -- getSelectedPath");
		// System.out.println(explorateurFichier.getselectedPath());		
	}
	
	public void setSelectedPath(String path) {
		this.path=path;
	}
	public String getSelectedPath() {
		return this.path;
	}
	public boolean getEnable() {
		return appui;
	}
	public void setEnable(boolean appui) {
		this.appui=appui;
	}
};
