package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;

import vue.explorateur.ExplorateurFichiers;


public class FenetrePrincipale extends JFrame implements ActionListener{
	
	public Position currentPosition = new Position();
	
	private static final long serialVersionUID = 1L;
		
	public String path;
	
	private JPanel panneauGauche = new JPanel(new BorderLayout());
	private JPanel panneauDroit = new JPanel(new BorderLayout());
	private JPanel commandes = new JPanel();
	private JPanel infoPosition = new JPanel();
	private JPanel parametre = new JPanel();

	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu fichier = new JMenu("Fichier");
	private JMenu aide = new JMenu("Aide");
	private JMenuItem info = new JMenu("Informations");
	private JMenuItem quitter = new JMenuItem("Quitter");
	
	private JFormattedTextField tempLitChauffant = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JFormattedTextField tempExtr1 = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JFormattedTextField tempExtr2 = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JFormattedTextField tempExtr3 = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JFormattedTextField tempExtr4 = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JFormattedTextField tempExtr5 = new JFormattedTextField(NumberFormat.getIntegerInstance());

	private JLabel temperature = new JLabel("Paramétrage Température");
	private JLabel tempAmbiant = new JLabel("Température Ambiante");
	private JLabel litChauffant = new JLabel("Température du lit chauffant");
	private JLabel extr1 = new JLabel("Extrudeur n°1");
	private JLabel extr2 = new JLabel("Extrudeur n°2");
	private JLabel extr3 = new JLabel("Extrudeur n°3");
	private JLabel extr4 = new JLabel("Extrudeur n°4");
	private JLabel extr5 = new JLabel("Extrudeur n°5");
	private JLabel positionLabel = new JLabel();
	
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
	private ExplorateurFichiers explorateurFichier;

	
	private JButton valider = new JButton();
	private JButton parcourir = new JButton("Parcourir...");

	public FenetrePrincipale() {

		
		this.setTitle("Printer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1920,1080);

		//Mettre en mode plein écran
		/*this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setVisible(true);*/
		
		
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
				valider.addActionListener(this);	
				parcourir.addActionListener(this);
				

			
					System.out.println("Fenetre Principale -- ");
					this.setTextEditor(explorateurFichier.getSelectedPath());
				
				
				//infos extrudeurs
				infoPosition.setLayout(new BoxLayout(infoPosition,BoxLayout.Y_AXIS));
				infoPosition.add(positionLabel);
				infoPosition.add(parcourir);
				parametre.setLayout(new BoxLayout(parametre,BoxLayout.Y_AXIS));
				valider.setText("Valider");
				parametre.add(temperature);
				parametre.add(tempAmbiant);
				parametre.add(litChauffant);
				parametre.add(tempLitChauffant);
				parametre.add(extr1);
				parametre.add(tempExtr1);
				parametre.add(extr2);
				parametre.add(tempExtr2);
				parametre.add(extr3);
				parametre.add(tempExtr3);
				parametre.add(extr4);
				parametre.add(tempExtr4);
				parametre.add(extr5);
				parametre.add(tempExtr5);
				parametre.add(valider);

				//boutons de commande des axes de l'imprimante
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
					
				
				editor.setAutoscrolls(true);
				editor.setSize(getMaximumSize());
				JScrollPane editorScrollPane =new JScrollPane(editor);
				editorScrollPane.setAutoscrolls(true);
				panneauGauche.add(editorScrollPane);
				panneauGauche.add(editor,BorderLayout.CENTER);	

				panneauDroit.add(commandes,BorderLayout.NORTH);
				panneauDroit.add(parametre,BorderLayout.SOUTH);
				panneauDroit.add(infoPosition,BorderLayout.CENTER);
		
				
	afficherMenu();
	commandes.setBackground(Color.CYAN);
	panneauDroit.setBackground(Color.green);
	panneauGauche.setBackground(Color.blue);
	editor.setBackground(Color.ORANGE);	
	parametre.setBackground(Color.PINK);
	
	
	
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
		if(arg0.getSource() == parcourir) {
			this.explorateurFichier = new ExplorateurFichiers("/");	
			}
		}	
	public void setTextEditor(String text) {
		this.editor.setText(text);
	}

}