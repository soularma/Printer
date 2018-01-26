package gcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.math.*;
import communication.PCA9685;
import vue.Position;
import raspberry.Gestion;



    
public class GCodeInterpretation extends raspberry.Moteur{
		private static PCA9685 servoBoard;
		private static int somme =0, valeur = -1, indice =-1;
        private static ArrayList<Integer> param = new ArrayList<Integer>();
        private static ArrayList<Integer> sauvegardeLigne = new ArrayList<Integer>();
        private static ArrayList<ArrayList<Integer>> sauvegardeFichier = new ArrayList<ArrayList<Integer>>();
		private static Position position = new Position();

        
        
        public static void GCodeInterpratation(File fileGcode) {
	      // Nous déclarons nos objets en dehors du bloc try/catch
	      FileInputStream fis = null;
	      try {
	         fis = new FileInputStream(fileGcode);
	         // On crée un tableau de byte pour indiquer le nombre de bytes lus à
	         // chaque tour de boucle
	         byte[] buf = new byte[8];
	         // On crée une variable de type int pour y affecter le résultat de
	         // la lecture
	         // Vaut -1 quand c'est fini
	         int n = 0;
	         // Tant que l'affectation dans la variable est possible, on boucle
	         // Lorsque la lecture du fichier est terminée l'affectation n'est
	         // plus possible !
	         // On sort donc de la boucle

	         while ((n = fis.read(buf)) >= 0) {

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
	               }
	            }
	            //Nous réinitialisons le buffer à vide
	            //au cas où les derniers byte lus ne soient pas un multiple de 8
	            //Ceci permet d'avoir un buffer vierge à chaque lecture et ne pas avoir de doublon en fin de fichier
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
	   }
        
       public void gestionFichier(){
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
	            		   int tempPause = lecture.get(j+3);
	            		   PCA9685.waitfor(tempPause);
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
	            	   case 29: //autolevel : calibration de Z sur 3 pts pour calculer la planéité du plateau.
	            		   break;
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
	            				   Position.setOrigineX(lecture.get(k+1));
	            				   k= k+2;
	            				   break;
	            			   case 89: 
	            				   Position.setOrigineY(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            			   case 90:
	            				   Position.setOrigineZ(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            			   case 85:
	            				   Position.setOrigineU(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            			   case 86:
	            				   Position.setOrigineV(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            			   case 87:
	            				   Position.setOrigineW(lecture.get(k+1));
	            				   k=k+2;
	            				   break;
	            				   default: 
	            					   System.out.println("Erreur");
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
	            	   j++;
	            	   break;
	               case 70:	//vitesse de déplacement, commande F, mm/s
	            	   System.out.print("Vitesse déplacement");
	            	   PCA9685.setVitesse(valeurParam);
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
	            		   Position.setOrigineX(x);
	            		   Position.setOrigineY(y);
	            		   Position.setOrigineZ(z);
	            		   Position.setOrigineU(u);
	            		   Position.setOrigineV(v);
	            		   Position.setOrigineW(w);
	            		   j=lecture.size();
	            		   break;
	            	   /*case 83: //definir le mode relatif pour l'extrudeur
	            		   break;*/
	            	   case 92: // definir le steps/mm
	            		   servoBoard.setStep(lecture.get(j+3));
	            		   j=lecture.size();
	            		   break;
	            	   case 104: //définir la température de l'extrudeur
	            		   Gestion.setTemperatureExtrud(valeurParam);
	            		   j=lecture.size();
	            		   break;
	            	   case 105: //retourner température de l'extrudeur et du plateau chauffant
	            		   String tempExtrudeur = Gestion.getTemperatureExtrud();
	            		   String tempBed = Gestion.getTemperatureBed();
	            		   System.out.println("La température de l'extrudeur est de :"+tempExtrudeur);	            		   
	            		   System.out.println("La température du lit chauffant est de :"+tempBed);	            		  
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
	            	   case 108: //définir la vitesse d'extrusion
	            		   break;
	            	   case 109: //define temp of extrudeur and wait
	            		   int temp = lecture.get(j+3); 
	            		   int m =0;
	            		   Gestion.setTemperatureBed(temp);
	            		   int sommeTemp=0;
	            		   while(sommeTemp < temp){
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
	            		   
	            		   System.out.println("La température est de: "+ sommeTemp);
	            		   }
	            		   
	            		   break;
	            	   case 110: //Set Current Line Number
	            		   int newLine = lecture.get(j+3); 
	            		   i = newLine;
	            		   break;
	            	   case 112: // Arret d'urgence
	            		   Gestion.arretUrgence();
	            		   servoBoard.StopAxe();
	            		   j=lecture.size();
	            		   break;
	            	   case 114: //Retourner la postion courante des axes
	            		   break;
	            	   case 116: //Attendre la température
	            		   break;
	            	   case 117: //afficher un message
	            		   break;
	            	   case 119: //retourner statuts des fin de courses
	            		   break;
	            	   case 140: //Définir la température du lit chauffant
	            		   break;
	            	   case 190: // Wait for bed temperature to reach target temp
	            		   	break;
	            	   case 200: //Set filament diameter
	            		   break;
	            	   case 201: //Définir l'accélaration maximum d'impréssion
	            		   break;
	            	   case 202: //Définir la vitesse maximum d'accélération de déplacement des axes
	            		   break;
	            	   case 203: //Définir la vitesse maximum de déplacement des axes
	            		   break;
	            		   default:
	            			   System.out.println("Erreur de paramètre!");
	            			   j = lecture.size();
	            	   }
	            	   break;
	               case 80:	//temps de pause, Commande P
	            	   System.out.print("Pause");
	            	   PCA9685.waitfor(valeurParam);
	            	   break;
	            /*--------------------------------
	             *| Coordonnées X, Y, Z, U, V, W |
	             *--------------------------------
	             */
	               case 88: //X
	            	   System.out.print("Axe X");
	            	   //setX(somme);
	            	   servoBoard.setChannel(0);
	            	   break;
	               case 89://Y
	            	   System.out.print("Axe Y");
	            	   //setY(somme)
	            	   servoBoard.setChannel(1);
	            	   break;
	               case 90://Z
	            	   System.out.print("Axe Z");
	            	   servoBoard.setChannel(2);
	            	   break;
	               case 85:	//position relative ou axe secondaire, commande U
	            	   System.out.print("Axe U");
	            	   servoBoard.setChannel(3);
	            	   break;
	               case 86:	//position relative ou axe secondaire, commande V
	            	   System.out.print("Axe V");
	            	   servoBoard.setChannel(4);
	            	   break;
	               case 87:	//position relative ou axe secondaire, commande W
	            	   System.out.print("Axe W");
	            	   servoBoard.setChannel(5);
	            	   break;
	            //////////////////////
	               case 73:	//commande I
	            	   
	            	   break;
	               case 74:	//commande J
	            	   
	            	   break;
	               case 68:	//COMMANDE D
	   
	            	   break;
	               case 72:	//utilisé pour la chauffe des resistance du PID, commande H
	            	   
	            	   break;
	
	               case 82:	//paramètre pour la température, commande R
	            	   	
	            	   break;
	               case 69:	//Longueur de matière extrudée, commande E
	            	   
	            	   break;
	               case 78:	//Commande N
	            	   
	            	   break;
	               case 42:	//Commande *
	   
	            	   break;
	               }
    			   
    		   }
    	   }
       }
	}