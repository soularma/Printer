package vue.explorateur;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;

public class EditeurText extends JPanel{


	private static final long serialVersionUID = 1L;
	//régler la hauteur de l'éditeur --> proportion entre la hauteur de la console et la hauteur de l'éditeur
	private JTextArea area = new JTextArea(40,140);
	private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
	private String currentFile = "Untitled";
	private boolean changed = false;	
	
	private String infoMessage;

	public EditeurText() {
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		JToolBar tool = new JToolBar();
		add(tool,BorderLayout.NORTH);
		
		tool.add(Open);
		tool.add(Save);
		
		tool.addSeparator();
		
		JButton svas = tool.add(SaveAs), cut = tool.add(Cut), cop = tool.add(Copy),pas = tool.add(Paste);
		
		cut.setText("Couper"); 
		cop.setText("Copier"); 
		pas.setText("Coller"); 
		svas.setText("Save as");
		
		JMenuBar JMB = new JMenuBar();
		this.add(JMB);
		JMenu file = new JMenu("File");
		
		file.add(Quit);
		file.addSeparator();
		
		area.setFont(new Font("Monospaced",Font.PLAIN,14));
		JScrollPane scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scroll);
		
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
		
		
		this.repaint();
		area.addKeyListener(k1);
		setVisible(true);
	}
	
	private KeyListener k1 = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			changed = true;
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
		}
	};
	
	//Ouvrir un fichier
	Action Open = new AbstractAction("Open") {
	
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			saveOld();
			if(dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				readInFile(dialog.getSelectedFile().getAbsolutePath());
			}
			infoMessage = dialog.getSelectedFile().getAbsolutePath();
			SaveAs.setEnabled(true);
		}
	};
	
	//Sauvegarder un fichier à l'emplacement actuel
	Action Save = new AbstractAction("Save") {
	
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if(!currentFile.equals("Untitled"))
				saveFile(currentFile);
			else
				saveFileAs();
		}
	};
	
	//Sauvegarder sous un autre nom
	Action SaveAs = new AbstractAction("Save as...") {
	
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};
	
	//Fermer l'onglet actif
	Action Quit = new AbstractAction("Quit") {
			
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveOld();
			System.exit(0);		
		}
	};
	
	private void saveFileAs() {
		if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}
	
	private void saveOld() {
		if(changed) {
			if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
	}
	
	private void readInFile(String fileName) {
		try {
			FileReader r = new FileReader(fileName);
			area.read(r,null);
			r.close();
			currentFile = fileName;
			changed = false;
		}
		catch(IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this,"Editor can't find the file called "+fileName);
		}
	}
	
	private void saveFile(String fileName) {
		try {
			FileWriter w = new FileWriter(fileName);
			area.write(w);
			w.close();
			currentFile = fileName;
			changed = false;
			Save.setEnabled(false);
		}
		catch(IOException e) {
			infoMessage = "Impossible de sauvegarder le fichier ! ";
		}
	}
	
	public String getInfoMessage() {
		return this.infoMessage;
	}
	
	//Mapping des options
	ActionMap m = area.getActionMap();
		Action Cut = m.get(DefaultEditorKit.cutAction);
		Action Copy = m.get(DefaultEditorKit.copyAction);
		Action Paste = m.get(DefaultEditorKit.pasteAction);

}
