package gcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.pi4j.io.gpio.GpioController;
import communication.PCA9685;
import vue.Position;
import raspberry.Gestion;
import raspberry.Moteur;



    
public class GCodeInterpretation extends raspberry.Moteur{
		private PCA9685 servoBoard;
		private int somme=0, valeur=-1, indice=-1;
		private Position position = new Position();
		private Position origine = new Position();
		private String info;
		private GpioController gpio;
		
		/*
		 * Cette fonction prend en paramètre un fichier, vérifie son extension car l'extension du fichier Gcode peut être .g, .gco or .gcode.
		 * Si l'extension du fichier est correcte, les fonction GCodeInterpretationLecture(File file) et gestionFichier() sont appelées.
		 * Dans le cas contraire on affiche un message d'erreur sur l'IHM.
		 */
		public GCodeInterpretation(File fileGcode){
			String fichier = "fileGcode"; 
			String ext = fichier.substring(fichier.lastIndexOf("."));
			if(ext!= "g" || ext != "gco" || ext != "gcode"){
		        ArrayList<ArrayList<Integer>> sauvegardeFichier = GCodeInterpretationLecture(fileGcode);
				gestionFichier(sauvegardeFichier);
			}else{
				info = "Le fichier n'est pas au bon format et ne peut être interprèter";
			}
		}
        
		/*
		 * Cette fonction prend en paramètre un objet de type fichier et renvoie une ArrayList<ArrayList<Integer>>.
		 * Elle traite ligne par ligne le fichier en paramètre. 
		 * Chaque ligne est traduite dans une arraylist et une deuxième arraylist
		 * engloble les arraylists correspondant au ligne du fichier.
		 * Le résultat de ce fichier est une ArrayList où à chaque index se trouve une ligne du fichier en  entrée.
		 */
        
        public ArrayList<ArrayList<Integer>> GCodeInterpretationLecture(File fileGcode) {
	      FileInputStream fis = null;
	      ArrayList<Integer> param = new ArrayList<Integer>();
	      ArrayList<Integer> sauvegardeLigne = new ArrayList<Integer>();
	      ArrayList<ArrayList<Integer>> sauvegardeFichier = new ArrayList<ArrayList<Integer>>();
	      try {
	         fis = new FileInputStream(fileGcode);
	         byte[] buf = new byte[8];
			while ((fis.read(buf)) >= 0) {

	            for (byte bit : buf) {
	               System.out.print("\t" + bit + "(" + (char) bit + ")");
	               
	               if( bit > 64 && bit < 122){
	            	   sauvegardeLigne.add((int)bit);
	            	   indice = 0;
	               }else if( bit < 47 && bit > 58){	//chiffres
	            	   switch(bit){
	            	   case 48:
	            		   valeur = 0;
	            		 break;
	            	   case 49:
	            		   valeur = 1;
	            		 break;
	            	   case 50:
	            		   valeur = 2;
	            		 break;
	            	   case 51:
	            		   valeur = 3;
	            		 break;
	            	   case 52:
	            		   valeur = 4;
	            		 break;
	            	   case 53:
	            		   valeur = 5;
	            		 break;
	            	   case 54:
	            		   valeur = 6;
			            break;
	            	   case 55:
	            		   valeur = 7;
	            		 break;
	            	   case 56:
	            		   valeur = 8;
	            		 break;
	            	   case 57:
	            		   valeur = 9;
	            		 break;
	            	   default: 
	            		   valeur =-1;
	            		   System.out.println("Erreur de paramètre!");
	            		   info = "Erreur de paramètre";
	            	   }
	            	   param.add( valeur);
	            	   indice = 1;
	            	   valeur = -1;
	               	}else if(bit == 32 && indice == 1){	//espace/ retour charriot/ Line feed 
	            	   int taille = param.size();
	            	   while( taille != 0){
	            		   somme = (int)Math.pow((double)10, (double)(taille-1))*(int)param.get(taille) + somme;
	            		   taille--;
	            	   }sauvegardeLigne.add(somme);
	            	   
	               }else if(bit == 13 || bit == 10){
	            	   sauvegardeFichier.add(sauvegardeLigne);
	            	   somme = 0;
	            	   valeur = -1;
	            	   param.clear();
	            	   sauvegardeLigne.clear();
	               }else{
	            	   System.out.println("Erreur de commande");
	            	   info = "Erreur de commande!";
	               }
	            }
	            //réinitialisation du buffer à vide
	            buf = new byte[8];
	         }

	         System.out.println("Interprétation terminé !");
	      } catch (FileNotFoundException e) {
	         // Cette exception est levée si l'objet FileInputStream ne trouve
	         // aucun fichier
	         e.printStackTrace();
	      } catch (IOException e) {
	         // Celle-ci se produit lors d'une erreur d'écriture ou de lecture
	         e.printStackTrace();
	      } finally {
	         // On ferme nos flux de données dans un bloc finally pour s'assurer
	         // que ces instructions seront exécutées dans tous les cas même si
	         // une exception est levée !
	         try {
	            if (fis != null)
	               fis.close();

	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	      return sauvegardeFichier;
	   }
        
        /*
         * 
         */
        
       public void gestionFichier(ArrayList<ArrayList<Integer>> sauvegardeFichier){
    	   int outil = 0;
    	   for(int i = 0; i  < sauvegardeFichier.size(); i++){
    		   ArrayList<Integer> lecture = sauvegardeFichier.get(i);
    		   int j =0;
    		   while(j< lecture.size()){
    			   int valeur = lecture.get(j);
    			   int valeurParam = lecture.get(j+1);
    			   switch((byte) valeur){
    			   /*----------------------------------------------
    			    *| Déplacement axes et commandes prédéfinies G |
    			    *----------------------------------------------
    			    */
	               case 71:
	            	   System.out.print("Déplacement axes: commande G");
	            	   switch(valeurParam){
	            	   case 0: //déplacement rapide
	            		   break;
	            	   case 1: //déplacement Linéaire
	            		   break;
	            	   case 2: //cercle sans des aiguilles
	            		   break;
	            	   case 3: //cercle sens antihoraire
	            		   break;
	            	   case 4 : //pause
	            		   PCA9685.waitfor(lecture.get(j+3));
	            		   j = lecture.size();
	            		   break;
	            	   case 20: //définir les unités en pouces
	            		   Position.setUnity(false);
	            		   j = lecture.size();
	            		   break;
	            	   case 21: //définir les unités en mm
	            		   Position.setUnity(true);
	            		   j = lecture.size();
	            		   break;
	            	   case 28: //déplacement aux origines
	            		   servoBoard.setHome();
	            		   j = lecture.size();
	            		   break;
	            	   /*case 29: //autolevel : calibration de Z sur 3 pts pour calculer la planéité du plateau.
	            		   break;*/
	            	   case 90: //position absolue: les coordonnées éxécutées à partir de maintenant sont en rapport à l'origine de la machine
	            		   position.setOrigine();
	            		   j=lecture.size();
	            		   break;
	            	   case 91: // Position relative: Toutes les coordonnées à partir de maintenant sont liés à la dernière position
	            		   int k = 3;
	            		   while(k < lecture.size()){
	            			   int valeurPosition = lecture.get(k);
	            			   switch((byte)valeurPosition){
	            			   case 88:
	            				   origine.setOrigineX(lecture.get(k+1));
	            				   k= k+2;
	            				   break;
	            			   case 89: 
	            				   origine.setOrigineY(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            			   case 90:
	            				   origine.setOrigineZ(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            			   case 85:
	            				   origine.setOrigineU(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            			   case 86:
	            				   origine.setOrigineV(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            			   case 87:
	            				   origine.setOrigineW(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            				   default: 
	            					   System.out.println("Erreur");
	            					   info = "Erreur position Origine!";
	            					   k=lecture.size();
	            			   }
	            		   }
	            		   break;
	            	   case 92 : //Définir la postion
	            		   int k1 = 3;
	            		   while(k1 < lecture.size()){
	            			   int valeurPosition = lecture.get(k1);
	            			   switch((byte)valeurPosition){
	            			   case 88:
	            				   position.setX(lecture.get(k1+1));
	            				   k1= k1+2;
	            				   break;
	            			   case 89: 
	            				   position.setY(lecture.get(k1+1));
	            				   k1=k1+2;
	            				   break;
	            			   case 90:
	            				   position.setZ(lecture.get(k1+1));
	            				   k1=k1+2;
	            				   break;
	            			   case 85:
	            				   position.setU(lecture.get(k1+1));
	            				   k1=k1+2;
	            				   break;
	            			   case 86:
	            				   position.setV(lecture.get(k1+1));
	            				   k1=k1+2;
	            				   break;
	            			   case 87:
	            				   position.setW(lecture.get(k1+1));
	            				   k1=k1+2;
	            				   break;
	            				   default: 
	            					   System.out.println("Erreur");
	            					   info = "Erreur de position!";
	            					   k1=lecture.size();
	            			   }
	            		   }
	            		   break;
	            	   	default:
	            		   j++;
	            	   }
	            	   break;
	               case 84:	//sélection outils commande T
	            	   System.out.print("Selection outils");
	            	   servoBoard.setChannel(valeurParam);
	            	   outil=valeurParam;
	            	   j++;
	            	   break;
	               case 70:	//vitesse de déplacement, commande F, mm/s
	            	   System.out.print("Vitesse déplacement");
	            	   servoBoard.setVitesse(valeurParam);
	            	   j++;
	            	   break;
	            	   /*------------------------------------
	            	    *| Code fonction machine, commande M |
	            	    *------------------------------------
	            	    */
	               case 77:	
	            	   System.out.print("Fonction ");
	            	   switch(valeurParam){
	            	   case 18: //Arret des moteurs
	            		   servoBoard.StopAxe();
	            		   j++;
	            		   break;
	            	   case 82: //definir le mode absolue pour l'extrudeur
	            		   int x = position.getX(), y = position.getY(), z = position.getZ(), u=position.getU(), v=position.getV(), w=position.getW();
	            		   origine.setOrigineX(x);
	            		   origine.setOrigineY(y);
	            		   origine.setOrigineZ(z);
	            		   origine.setOrigineU(u);
	            		   origine.setOrigineV(v);
	            		   origine.setOrigineW(w);
	            		   j=lecture.size();
	            		   break;
	            	   /*case 83: //definir le mode relatif pour l'extrudeur
	            		   break;*/
	            	   case 92: // definir le steps/mm
	            		   servoBoard.setStep(lecture.get(j+3));
	            		   j=lecture.size();
	            		   break;
	            	   case 104: //définir la température de l'extrudeur
	            		   Gestion.setTemperatureExtrud(outil, valeurParam);
	            		   j=lecture.size();
	            		   break;
	            	   case 105: //retourner température de l'extrudeur et du plateau chauffant
	            		   String tempExtrudeur = Gestion.getTemperatureExtrud();
	            		   String tempBed = Gestion.getTemperatureBed();
	            		   info = "La température de l'extrudeur est de :"+tempExtrudeur;
	            		   info = "La température du lit chauffant est de :"+tempBed;	            		  
	            		   j=lecture.size();
	            		   break;
	            	   case 106://Allumer les ventilateurs
	            		   Gestion.fanOn();
	            		   j=lecture.size();
	            		   break;
	            	   case 107://Eteindre les ventilateurs
	            		   Gestion.fanOff();
	            		   j=lecture.size();
	            		   break;
	            	   /*case 108: //définir la vitesse d'extrusion
	            		   break;*/
	            	   case 109: //define temp of extrudeur and wait
	            		   int m =0;
	            		   Gestion.setTemperatureBed(lecture.get(j+3));
	            		   int sommeTemp=0;
	            		   while(sommeTemp < lecture.get(j+3)){
	            		   String tempExtrud = Gestion.getTemperatureExtrud();
            			   ArrayList<Integer> paramTemp = new ArrayList<Integer>();
	            		   while( m <tempExtrud.length()){
	            			   switch(tempExtrud.charAt(m)){
	            			   case '0':
	            				   paramTemp.add(0);
	            				   m++;
	            				   break;
	            			   case '1':
	            				   paramTemp.add(1);
	            				   m++;
	            				   break;
	            			   case '2':
	            				   paramTemp.add(2);
	            				   m++;
	            				   break;
	            			   case '3':
	            				   paramTemp.add(3);
	            				   m++;
	            				   break;
	            			   case '4':
	            				   paramTemp.add(4);
	            				   m++;
	            				   break;
	            			   case '5':
	            				   paramTemp.add(5);
	            				   m++;
	            				   break;
	            			   case '6':
	            				   paramTemp.add(6);
	            				   m++;
	            				   break;
	            			   case '7':
	            				   paramTemp.add(7);
	            				   m++;
	            				   break;
	            			   case '8':
	            				   paramTemp.add(8);
	            				   m++;
	            				   break;
	            			   case '9':
	            				   paramTemp.add(9);
	            				   m++;
	            				   break;
	            				   default:
	            					   m++;   
	            			   }
	            		   }
	            		   int taille = paramTemp.size();
	            		   while( taille != 0){
		            		   sommeTemp = (int)Math.pow((double)10, (double)(taille-1))*paramTemp.get(taille) + sommeTemp;
		            		   taille--;   
	            		   }
	            		   info = "La température est de: "+ sommeTemp;
	            		   }
	            		   j=lecture.size();
	            		   break;
	            	   case 110: //Set Current Line Number
	            		   i = lecture.get(j+3);
	            		   j=lecture.size();
	            		   break;
	            	   case 112: // Arret d'urgence
	            		   Gestion.arretUrgence();
	            		   servoBoard.StopAxe();
	            		   j=lecture.size();
	            		   break;
	            	   case 114: //Retourner la postion courante des axes
	            		   info = "Axe X: " + position.getX();
	            		   info = "Axe Y: " + position.getY();
	            		   info = "Axe Z: " + position.getZ();
	            		   info = "Axe U: " + position.getU();
	            		   info = "Axe V: " + position.getV();
	            		   info = "Axe W: " + position.getW();
	            		   j=lecture.size();
	            		   break;
	            	   case 116: //Attendre la température
	            		   int l =0;
	            		   int sommeTempWait=0;
	            		   while(sommeTempWait < lecture.get(j+3)){
	            		   String tempExtrud = Gestion.getTemperatureExtrud();
            			   ArrayList<Integer> paramTemp = new ArrayList<Integer>();
	            		   while( l <tempExtrud.length()){
	            			   switch(tempExtrud.charAt(l)){
	            			   case '0':
	            				   paramTemp.add(0);
	            				   l++;
	            				   break;
	            			   case '1':
	            				   paramTemp.add(1);
	            				   l++;
	            				   break;
	            			   case '2':
	            				   paramTemp.add(2);
	            				   l++;
	            				   break;
	            			   case '3':
	            				   paramTemp.add(3);
	            				   l++;
	            				   break;
	            			   case '4':
	            				   paramTemp.add(4);
	            				   l++;
	            				   break;
	            			   case '5':
	            				   paramTemp.add(5);
	            				   l++;
	            				   break;
	            			   case '6':
	            				   paramTemp.add(6);
	            				   l++;
	            				   break;
	            			   case '7':
	            				   paramTemp.add(7);
	            				   l++;
	            				   break;
	            			   case '8':
	            				   paramTemp.add(8);
	            				   l++;
	            				   break;
	            			   case '9':
	            				   paramTemp.add(9);
	            				   l++;
	            				   break;
	            				   default:
	            					   l++;   
	            			   }
	            		   }
	            		   int taille = paramTemp.size();
	            		   while( taille != 0){
		            		   sommeTempWait = (int)Math.pow((double)10, (double)(taille-1))*paramTemp.get(taille) + sommeTempWait;
		            		   taille--;   
	            		   }
	            		   info = "La température est de: "+ sommeTempWait;
	            		   }
	            		   j=lecture.size();
	            		   break;
	            	   /*case 117: //afficher un message
	            		   break;*/
	            	   case 119: //retourner statuts des fin de courses
	            		   int [] nbEndStop;
	            		   nbEndStop = Gestion.askEndStop();
		            		   info = "Etat capteur fin de course X+: "+nbEndStop[7]+"\n"+"Etat capteur fin de course Y+: "+nbEndStop[5]+"\n"+"Etat capteur fin de course Z+: "+nbEndStop[3]+"\n"+"Etat capteur fin de course U+: "+nbEndStop[1]+"\n"+"Etat capteur fin de course V+: "+nbEndStop[11]+"\n"+"Etat capteur fin de course W+: "+nbEndStop[9]+"\n";
		            		   info += "Etat capteur fin de course X-: "+nbEndStop[6]+"\n"+"Etat capteur fin de course Y-: "+nbEndStop[4]+"\n"+"Etat capteur fin de course Z-: "+nbEndStop[2]+"\n"+"Etat capteur fin de course U-: "+nbEndStop[0]+"\n"+"Etat capteur fin de course V-: "+nbEndStop[10]+"\n"+"Etat capteur fin de course W-: "+nbEndStop[8]+"\n";
	            		   j=lecture.size();
	            		   break;
	            	   case 140: //Définir la température du lit chauffant
	            		   Gestion.setTemperatureBed(lecture.get(j+3));
	            		   j=lecture.size();
	            		   break;
	            	   case 190: // Wait for bed temperature to reach target temp 
	            		   int l1 =0;
	            		   int sommeTempWait1=0;
	            		   while(sommeTempWait1 < lecture.get(j+3)){
	            		   String tempBed1 = Gestion.getTemperatureBed();
            			   ArrayList<Integer> paramTempBed = new ArrayList<Integer>();
	            		   while( l1 <tempBed1.length()){
	            			   switch(tempBed1.charAt(l1)){
	            			   case '0':
	            				   paramTempBed.add(0);
	            				   l1++;
	            				   break;
	            			   case '1':
	            				   paramTempBed.add(1);
	            				   l1++;
	            				   break;
	            			   case '2':
	            				   paramTempBed.add(2);
	            				   l1++;
	            				   break;
	            			   case '3':
	            				   paramTempBed.add(3);
	            				   l1++;
	            				   break;
	            			   case '4':
	            				   paramTempBed.add(4);
	            				   l1++;
	            				   break;
	            			   case '5':
	            				   paramTempBed.add(5);
	            				   l1++;
	            				   break;
	            			   case '6':
	            				   paramTempBed.add(6);
	            				   l1++;
	            				   break;
	            			   case '7':
	            				   paramTempBed.add(7);
	            				   l1++;
	            				   break;
	            			   case '8':
	            				   paramTempBed.add(8);
	            				   l1++;
	            				   break;
	            			   case '9':
	            				   paramTempBed.add(9);
	            				   l1++;
	            				   break;
	            				   default:
	            					   l1++;   
	            			   }
	            		   }
	            		   int taille = paramTempBed.size();
	            		   while( taille != 0){
		            		   sommeTempWait1 = (int)Math.pow((double)10, (double)(taille-1))*paramTempBed.get(taille) + sommeTempWait1;
		            		   taille--;   
	            		   }
	            		   info = "La température du lit est de: "+ sommeTempWait1;
	            		   }
	            		   j=lecture.size();
	            		   	break;
	            	   case 200: //Set filament diameter
	            		   ((Moteur) gpio).setDiameter(lecture.get(j+3));
	            		   j=lecture.size();
	            		   break;
	            	   case 202: //Définir l'accélaration maximum d'impréssion
	            		   servoBoard.setAccelerationMax(lecture.get(j+3));
	            		   j = lecture.size();
	            		   break;
	            	   case 203: //Définir la vitesse maximum de déplacement des axes
	            		   servoBoard.setVitesseMax(lecture.get(j+3));
	            		   j = lecture.size();
	            		   break;
	            		   default:
	            			   System.out.println("Erreur de paramètre!");
	            			   j = lecture.size();
	            	   }
	            	   break;
	               case 80:	//temps de pause, Commande P
	            	   System.out.print("Pause");
	            	   PCA9685.waitfor(valeurParam);
            		   j=lecture.size();
	            	   break;
	            /*--------------------------------
	             *| Coordonnées X, Y, Z, U, V, W |
	             *--------------------------------
	             */
	               case 88: //X
	            	   System.out.print("Axe X");
	            	   servoBoard.setChannel(0);
	            	   position.setX(valeurParam);
	            	   j++;
	            	   break;
	               case 89://Y
	            	   System.out.print("Axe Y");
	            	   servoBoard.setChannel(1);
	            	   position.setY(valeurParam);
	            	   j++;
	            	   break;
	               case 90://Z
	            	   System.out.print("Axe Z");
	            	   servoBoard.setChannel(2);
	            	   position.setZ(valeurParam);
	            	   j++;
	            	   break;
	               case 85:	//position relative ou axe secondaire, commande U
	            	   System.out.print("Axe U");
	            	   servoBoard.setChannel(3);
	            	   position.setU(valeurParam);
	            	   j++;
	            	   break;
	               case 86:	//position relative ou axe secondaire, commande V
	            	   System.out.print("Axe V");
	            	   servoBoard.setChannel(4);
	            	   position.setV(valeurParam);
	            	   j++;
	            	   break;
	               case 87:	//position relative ou axe secondaire, commande W
	            	   System.out.print("Axe W");
	            	   servoBoard.setChannel(5);
	            	   position.setW(valeurParam);
	            	   j++;
	            	   break;
	              /* case 73:	//commande I
	            	   
	            	   break;
	               case 74:	//commande J
	            	   
	            	   break;
	              case 72:	//utilisé pour la chauffe des resistance du PID, commande H
	            	   break;
	            	case 82:	//paramètre pour la température, commande R
	            	   break;
	               case 69:	//Longueur de matière extrudée, commande E
	            	   break;*/
	            	   default:
	            		   j++;
	               }   
    		   }
    	   }
       }
       public String getInfo(){
    	   return this.info;
       }
	}