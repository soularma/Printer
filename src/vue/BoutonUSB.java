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
		listRoot();
	}
	
	
	public void listRoot() {
		JFrame treeView = new JFrame(); 
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode();	

		treeView.setTitle("Select GCode File :");	
		treeView.setSize(400, 600);
		
		for(File file : File.listRoots()) {
			DefaultMutableTreeNode lecteur = new DefaultMutableTreeNode(file.getAbsolutePath());
			try {
				for(File nom : file.listFiles()) {
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nom.getName() + "\\");
					lecteur.add(listFile(nom,node));
				}
			} catch(NullPointerException e){}		
			racine.add(lecteur);
		}
		
		JTree arbre = new JTree(racine);
		arbre.setRootVisible(false);
		arbre.addTreeSelectionListener(new TreeSelectionListener(){
				
			public void valueChanged(TreeSelectionEvent event) {
				if(arbre.getLastSelectedPathComponent() != null){
				      //La méthode getPath retourne un objet TreePath
				      System.out.println(getAbsolutePath(event.getPath()));
				}
			}
			
			private String getAbsolutePath(TreePath treePath){
				String str = "";
				    //On balaie le contenu de l'objet TreePath
				for(Object name : treePath.getPath()){
				    if(name.toString() != null)
				        str += name.toString();
				    }
				    return str;
				}
		});
		
		treeView.getContentPane().add(new JScrollPane(arbre));
		System.out.println("Ouverture de l'arborescence de fichiers");
		treeView.setVisible(true);
		
	}
	
	private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node) {
				
		if(file.isFile()) {
			return new DefaultMutableTreeNode(file.getName());
		}else {
			File[] list = file.listFiles();
			if(list == null) {
				return new DefaultMutableTreeNode(file.getName());
			}					
			for(File nom : list) {	
		          DefaultMutableTreeNode subNode;		          
		          subNode = new DefaultMutableTreeNode(nom.getName());		     
		          node.add(subNode);
		      }
		      return node;			
		}
	}
	
};
