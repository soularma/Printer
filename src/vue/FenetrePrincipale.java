package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.*;
import javax.swing.*;

public class FenetrePrincipale extends JFrame implements ActionListener{
	
	private JMenuBar menunBar = new JMenuBar();
	
	private JMenu fichier = new JMenu("Fichier");
	private JMenu aide = new JMenu("Aide");
	private JMenuItem info = new JMenu("Informations");
	private JMenuItem quitter = new JMenuItem("Quitter");
	
	public JLabel abscisse = new JLabel();
	public JLabel ordonne = new JLabel();
	public JLabel hauteur = new JLabel();
	
	
	public FenetrePrincipale() {
		
		this.setTitle("Printer");
		this.setLocationRelativeTo(null);
		
		this.add(abscisse);
		this.add(abscisse);
		this.add(hauteur);
		
		
	}
	
	
	public void actionPerformed(ActionEvent arg0){
		
	}
	
}