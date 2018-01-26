package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.tree.TreePath;

import com.pi4j.io.gpio.exception.UnsupportedBoardType;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;

import vue.explorateur.EditeurText;
import communication.Uart;
import raspberry.Temperature;
import communication.I2C;
import communication.PCA9685;


public class FenetrePrincipale extends JFrame implements ActionListener{
	
	public Position currentPosition = new Position();
	
	private static final long serialVersionUID = 1L;
		
	public TreePath path;
	public Uart uart;
	public PCA9685 i2c;
	public Temperature gestionTemperature = new Temperature();
	
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
	
	private EditeurText editorText = new EditeurText();
	JScrollPane scrollPrompt; 
	public JTextArea console = new JTextArea();
	
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
	
	private JButton valider = new JButton();
	private JButton raz = new JButton("Remise à Zéro");
	private JButton clear = new JButton("Clear");
	private JButton envoyer = new JButton("Envoyer");

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
				raz.addActionListener(this);
				clear.addActionListener(this);
				
				
				
				
				
				//infos extrudeurs
				infoPosition.setLayout(new BoxLayout(infoPosition,BoxLayout.Y_AXIS));
				infoPosition.add(positionLabel);
				infoPosition.add(raz);
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
					
					try {
						uart = new Uart();
						this.uart.serial.addListener(new SerialDataEventListener() {
				            @Override
				            public void dataReceived(SerialDataEvent event) {

				               /* il est essentiel de lire les données reçues pour éviter un 
				                * encombrement du buffer en mémoire inutile
				                */
				            	
				                // écriture des données reçues sur la console
				                try {
				                    writePrompt("[HEX DATA]   " + event.getHexByteString());
				                    writePrompt("[ASCII DATA] " + event.getAsciiString());
				                } catch (IOException e) {
				                    e.printStackTrace();
				                }
				            }
				        });
					}catch (UnsupportedBoardType | InterruptedException e2) {
						e2.printStackTrace();
					}
					
					this.console.setLineWrap(true);
					this.console.setFont(new Font("Monospaced",Font.PLAIN,15));
				    this.console.setBackground(Color.BLACK);
				    this.console.setForeground(Color.LIGHT_GRAY);
				    this.console.setEditable(false);
				    
				    
				    scrollPrompt = new JScrollPane(console,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);    
				    editorText = new EditeurText();
					panneauGauche.setLayout(new BoxLayout(panneauGauche,BoxLayout.Y_AXIS));
					//console.setSize(new Dimension((int)panneauGauche.getWidth(),(int)panneauGauche.getHeight()/10));
					
					panneauGauche.add(editorText);
					panneauGauche.add(scrollPrompt);
					panneauGauche.add(clear);
					panneauGauche.add(envoyer);
					
				panneauDroit.add(commandes,BorderLayout.NORTH);
				panneauDroit.add(parametre,BorderLayout.SOUTH);
				panneauDroit.add(infoPosition,BorderLayout.CENTER);
	
	afficherMenu();
	this.getContentPane().add(panneauGauche,BorderLayout.WEST);
	this.getContentPane().add(panneauDroit,BorderLayout.EAST);
	this.setVisible(true);
		
	}
	
	
	public void actionPerformed(ActionEvent arg0){
		
		if(arg0.getSource() == boutonGauche) {
			this.currentPosition.setX(boutonGauche.position.getX());
			this.boutonDroit.position.setX(this.boutonGauche.position.getX());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		
		if(arg0.getSource() == boutonDroit) {
			this.currentPosition.setX(boutonDroit.position.getX());
			this.boutonGauche.position.setX(this.boutonDroit.position.getX());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonHaut) {
			this.currentPosition.setY(boutonHaut.position.getY());
			this.boutonBas.position.setY(this.boutonHaut.position.getY());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonBas) {
			this.currentPosition.setY(boutonBas.position.getY());
			this.boutonHaut.position.setY(this.boutonBas.position.getY());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonZBas) {
			this.currentPosition.setZ(boutonZBas.position.getZ());
			this.boutonZHaut.position.setZ(this.boutonZBas.position.getZ());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonZHaut) {
			this.currentPosition.setZ(boutonZHaut.position.getZ());
			this.boutonZBas.position.setZ(this.boutonZHaut.position.getZ());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonUDroit) {
			this.currentPosition.setU(boutonUDroit.position.getU());
			this.boutonUGauche.position.setU(this.boutonUDroit.position.getU());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonUGauche) {
			this.currentPosition.setU(boutonUGauche.position.getU());
			this.boutonUDroit.position.setU(this.boutonUGauche.position.getU());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonVDroit) {
			this.currentPosition.setV(boutonVDroit.position.getV());
			this.boutonVGauche.position.setV(this.boutonVDroit.position.getV());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == boutonVGauche) {
			this.currentPosition.setV(boutonVGauche.position.getV());
			this.boutonVDroit.position.setV(this.boutonVGauche.position.getV());
			this.positionLabel.setText(currentPosition.affichePos());
			this.writePrompt(currentPosition.affichePos());
		}
		if(arg0.getSource() == envoyer) {
			this.writePrompt("---------- Test I2C ----------");
			try {
				i2c = new PCA9685(PCA9685.PCA9685_ADDRESS);
				this.i2c.writeI2c((byte)0x44);
				this.writePrompt(this.i2c.getInfo());
			} catch (UnsupportedBusNumberException e) {
				this.writePrompt("Error ! Unable to connect PCA9685 !!!");
				e.printStackTrace();
			}
			envoyer.setEnabled(false);
		}
		if(arg0.getSource() == clear) {
			this.clearPrompt();
			
			this.envoyer.setEnabled(true);
		}
		if(arg0.getSource() == raz) {
			this.boutonBas.setOrigine();
			this.boutonHaut.setOrigine();
			this.boutonGauche.setOrigine();
			this.boutonDroit.setOrigine();
			this.boutonZBas.setOrigine();
			this.boutonZHaut.setOrigine();
			this.boutonUDroit.setOrigine();
			this.boutonUGauche.setOrigine();
			this.boutonVGauche.setOrigine();
			
			this.currentPosition = new Position();
			this.positionLabel.setText(currentPosition.affichePos());
			//this.writePrompt(currentPosition.affichePos());			
			}
		
			if(arg0.getSource() == valider) {
				this.tempExtr1.setText(this.tempExtr1.getText());
				this.tempExtr2.setText(this.tempExtr2.getText());
				this.tempExtr3.setText(this.tempExtr3.getText());
				this.tempExtr4.setText(this.tempExtr4.getText());
				this.tempExtr5.setText(this.tempExtr5.getText());
				this.tempLitChauffant.setText(this.tempLitChauffant.getText());
				
				this.gestionTemperature.setTempExtrudeur(this.tempExtr1.getText(), 1);
				this.gestionTemperature.setTempExtrudeur(this.tempExtr2.getText(), 2);
				this.gestionTemperature.setTempExtrudeur(this.tempExtr3.getText(), 3);
				this.gestionTemperature.setTempExtrudeur(this.tempExtr4.getText(), 4);
				this.gestionTemperature.setTempExtrudeur(this.tempExtr5.getText(), 5);
				this.gestionTemperature.setTempLit(this.tempLitChauffant.getText());
				
				//Envoi sur l'UART des données de températures	
				try {
						uart = new Uart();
					} catch (UnsupportedBoardType | InterruptedException e2) {
						e2.printStackTrace();
					}
					
					try {
						uart.getInfo();
						uart.write(this.gestionTemperature.toUart());
					} catch (IllegalStateException | IOException e2) {
						e2.printStackTrace();
					}
					this.writePrompt(this.gestionTemperature.getInfo());
			}
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
	
	public void writePrompt(String info) {
		this.console.setText(this.console.getText().concat(info) + "\n");
	}
	
	public void clearPrompt() {
		this.console.selectAll();
		this.console.replaceSelection("");
	}

}	