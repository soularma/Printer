package raspberry;

import java.util.ArrayList;

public class Data implements Observable{

		private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();

	
	
	@Override
	public void addObservateur(Observateur obs) {
		this.listObservateur.add(obs);
		
	}

	@Override
	public void updateObservateur() {
	    for(Observateur obs : this.listObservateur ) {
	        
	    }
	    
		
	}

	@Override
	public void delObservateur() {
		this.listObservateur = new ArrayList<Observateur>();		
	}

}
