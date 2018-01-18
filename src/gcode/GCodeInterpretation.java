package gcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.math.*;
import communication.PCA9685;



    
public class GCodeInterpretation extends raspberry.Moteur{
		private static PCA9685 servoBoard;
	 public static void GCodeInterpratation() {
	      // Nous déclarons nos objets en dehors du bloc try/catch
	      FileInputStream fis = null;



	      try {
	         fis = new FileInputStream(new File("test.txt"));
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
	            // On affiche ce qu'a lu notre boucle au format byte et au
	            // format char

	            for (byte bit : buf) {
	               System.out.print("\t" + bit + "(" + (char) bit + ")");
	               int somme =0, valeur = -1;
	               ArrayList<Integer> param = new ArrayList();
	               byte sauvegarde = 0;
	               if( bit > 64 && bit < 122){
	            	   sauvegarde = bit;
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
	            	   case 59:
	            		   valeur = 9;
	            		 break;
	            	   default: 
	            		   valeur =-1;
	            		   System.out.println("Erreur de paramètre!");
	            	   }
	            	   param.add( valeur);
	            	   valeur = -1;
	               }else if(bit == 32 || bit == 13 || bit == 10){	//espace/ retour charriot/ Line feed 
	            	   int taille = param.size();
	            	   while( taille != 0){
	            		   somme = (int)Math.pow((double)10, (double)(taille-1))*(int)param.get(taille) + somme;
	            		   taille--;
	            	   }switch(sauvegarde){
		               case 71: //déplacement axes et commande prédéfinier G
		            	   System.out.print("Déplacement axes: commande G");
		            	   break;
		               case 84:	//sélection outils commande T
		            	   System.out.print("Selection outils");
		            	   servoBoard.setChannel(somme);
		            	   break;
		               case 83:	//vitesse de broche commande S
		            	   System.out.print("Vitesse broche");
		            	   break;
		               case 70:	//vitesse de déplacement, commande F, mm/s
		            	   System.out.print("Vitesse déplacement");
		            	   servoBoard.setVitesse(somme);
		            	   break;
		               case 77:	//code fonction machine, commande M
		            	   System.out.print("Fonction ");
		            	   break;
		               case 80:	//temps de pause, Commande P
		            	   System.out.print("Pause");
		            	   servoBoard.waitfor(somme);
		            	   break;
		               /*case 'V':	//permet de contrôler une vitesse de rotation dans une machine avec diverses configurations
		            	   break;*/
		            ///////////////////
		            //coordonnées
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
		               case 65:	//rotation auttour de l'axe X, commande A
		            	   System.out.print("Rotation auttour de l'axe X");
		            	   break;
		               case 66:	//rotation autour de l'axe y, commande B
		            	   System.out.print("Rotation auttour de l'axe Y");
		            	   break;
		               case 67:	//rotation autour de l'axe z, commande C
		            	   System.out.print("Rotation auttour de l'axe Z");
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
	            	   param.clear();
	               }
	            }
	            System.out.println("");
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
	}