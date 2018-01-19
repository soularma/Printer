package vue.explorateur;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import vue.BoutonUSB;
import vue.FenetrePrincipale;

public class ExplorateurFichiers extends JFrame implements TreeSelectionListener{
	
	
	private static final long serialVersionUID = 1L;
	private File root;
	private TreeModel modele;
	private JTree tree; 
	private JTextArea infos;
	private File file;
	private TreePath path;
	public  String selectedPath;
	public FenetrePrincipale fen;
	
	public ExplorateurFichiers(String repertoire){
		super("Explorateur de fichiers");	
		root = new File(repertoire);
		modele = new FileTreeModel(root);
		tree = new JTree(modele);
		tree.setCellRenderer(new FileRenderer());
		tree.addTreeSelectionListener(this);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		infos = new JTextArea();
		infos.setEditable(false);
		infos.setLineWrap(true);
		infos.setWrapStyleWord(true);
				
		getContentPane().setLayout(new GridLayout(1,2));
		getContentPane().add(new JScrollPane(tree));
		getContentPane().add(new JScrollPane(infos));
		
		
		
		setSize(700,600);
		setLocationRelativeTo(null); 
		setVisible(true);
		
	}

	
	public void valueChanged(TreeSelectionEvent e){
		
		path = e.getPath();
		file = (File)path.getLastPathComponent();
		String s = "Can read : \n   "+file.canRead()+"\n";
		s += "Can write : \n   "+file.canWrite()+"\n";
		s += "Parent : \n   "+file.getParent()+"\n";
		s += "Name : \n   "+file.getName()+"\n";
		s += "Length : \n   "+file.length()+"\n";
		s += "Last modified : \n   "+new Date(file.lastModified())+"\n";
		infos.setText(s);
		selectedPath = path.getLastPathComponent().toString();
	}	
	
	public String getSelectedPath() {
		return this.selectedPath;
	}
	
}