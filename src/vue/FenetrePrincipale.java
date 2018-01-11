package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class FenetrePrincipale extends JFrame implements ActionListener{
	
	public Position currentPosition = new Position();
	
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar = new JMenuBar();
	private JPanel commandes = new JPanel();
	private JPanel panneauGauche = new JPanel();
	private JPanel panneauDroit = new JPanel();
	private JPanel affichageGCode = new JPanel();
	
	private JMenu fichier = new JMenu("Fichier");
	private JMenu aide = new JMenu("Aide");
	private JMenuItem info = new JMenu("Informations");
	private JMenuItem quitter = new JMenuItem("Quitter");
	
	
	public JLabel positionLabel = new JLabel();
	private JEditorPane editor = new JEditorPane();
	
	private BoutonXGauche boutonGauche = new BoutonXGauche();
	private BoutonXDroit boutonDroit = new BoutonXDroit();
	private BoutonYHaut boutonHaut = new BoutonYHaut();
	private BoutonYBas boutonBas = new BoutonYBas();
	private BoutonZBas boutonZBas = new BoutonZBas();
	private BoutonZHaut boutonZHaut = new BoutonZHaut();
	private BoutonUAvant boutonUDroit = new BoutonUAvant();
	private BoutonUArriere boutonUGauche = new BoutonUArriere();
	private BoutonVAvant boutonVDroit = new BoutonVAvant();
	private BoutonVArriere boutonVGauche = new BoutonVArriere();
	private BoutonUSB boutonUSB = new BoutonUSB();
	
	
	public FenetrePrincipale() {
		
		this.setTitle("Printer");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1920,1080);
		
		Font font = new Font("Tahoma", Font.BOLD, 20);
		positionLabel.setFont(font);
		positionLabel.setText(this.currentPosition.affichePos());
	
		
				//attente d'action avec la souris
				boutonGauche.addActionListener(this);
				boutonDroit.addActionListener(this);
				boutonHaut.addActionListener(this);
				boutonBas.addActionListener(this);
				boutonZHaut.addActionListener(this);
				boutonZBas.addActionListener(this);	
				boutonUDroit.addActionListener(this);
				boutonUGauche.addActionListener(this);
				boutonVDroit.addActionListener(this);
				boutonVGauche.addActionListener(this);
				boutonUSB.addActionListener(this);

				affichageGCode.add(editor, BorderLayout.CENTER);
				panneauGauche.add(affichageGCode,BorderLayout.SOUTH);
				
				panneauDroit.add(positionLabel);
				panneauDroit.add(boutonUSB);
				panneauDroit.add(commandes);
				
				commandes.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				
					gbc.gridx = 0;
					gbc.gridy = 0;
					commandes.add(boutonUGauche,gbc);
					
					gbc.gridx = 0;
					gbc.gridy = 1;
					commandes.add(boutonGauche, gbc);
					
					gbc.gridx = 0;
					gbc.gridy = 2;
					commandes.add(boutonVGauche,gbc);
					
					gbc.gridx = 1;
					gbc.gridy = 0;
					commandes.add(boutonHaut,gbc);
					
					gbc.gridx = 1;
					gbc.gridy = 2;
					commandes.add(boutonBas, gbc);
					
					gbc.gridx = 2;
					gbc.gridy = 0;
					commandes.add(boutonUDroit,gbc);
					
					gbc.gridx = 2;
					gbc.gridy = 1;
					commandes.add(boutonDroit,gbc);
					
					gbc.gridx = 2;
					gbc.gridy = 2;
					commandes.add(boutonVDroit,gbc);
					
					gbc.gridx = 4;
					gbc.gridy = 0;
					commandes.add(boutonZHaut, gbc);
					
					gbc.gridx = 4;
					gbc.gridy = 2;
					commandes.add(boutonZBas, gbc);		
				
	
	afficherMenu();
	commandes.setBackground(Color.CYAN);
	panneauDroit.setBackground(Color.green);
	panneauGauche.setBackground(Color.blue);
	affichageGCode.setBackground(Color.ORANGE);	
	this.getContentPane().add(panneauGauche,BorderLayout.WEST);
	this.getContentPane().add(panneauDroit,BorderLayout.EAST);
	this.setVisible(true);
		
	}
	
	public void afficherMenu() {
		quitter.setEnabled(true);
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		this.menuBar.add(fichier);
		this.menuBar.add(aide);
		fichier.add(quitter);
		aide.add(info);
		
		this.setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent arg0){
	
		if(arg0.getSource() == boutonGauche) {
			this.currentPosition.setX(boutonGauche.position.getX());
			this.boutonDroit.position.setX(this.boutonGauche.position.getX());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		
		if(arg0.getSource() == boutonDroit) {
			this.currentPosition.setX(boutonDroit.position.getX());
			this.boutonGauche.position.setX(this.boutonDroit.position.getX());
			this.positionLabel.setText(currentPosition.affichePos());
	
		}
		if(arg0.getSource() == boutonHaut) {
			this.currentPosition.setY(boutonHaut.position.getY());
			this.boutonBas.position.setY(this.boutonHaut.position.getY());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonBas) {
			this.currentPosition.setY(boutonBas.position.getY());
			this.boutonHaut.position.setY(this.boutonBas.position.getY());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonZBas) {
			this.currentPosition.setZ(boutonZBas.position.getZ());
			this.boutonZHaut.position.setZ(this.boutonZBas.position.getZ());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonZHaut) {
			this.currentPosition.setZ(boutonZHaut.position.getZ());
			this.boutonZBas.position.setZ(this.boutonZHaut.position.getZ());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonUDroit) {
			this.currentPosition.setU(boutonUDroit.position.getU());
			this.boutonUGauche.position.setU(this.boutonUDroit.position.getU());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonUGauche) {
			this.currentPosition.setU(boutonUGauche.position.getU());
			this.boutonUDroit.position.setU(this.boutonUGauche.position.getU());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonVDroit) {
			this.currentPosition.setV(boutonVDroit.position.getV());
			this.boutonVGauche.position.setV(this.boutonVDroit.position.getV());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonVGauche) {
			this.currentPosition.setV(boutonVGauche.position.getV());
			this.boutonVDroit.position.setV(this.boutonVGauche.position.getV());
			this.positionLabel.setText(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonUSB) {
			
		}
		
	}
}