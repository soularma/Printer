package raspberry;

import communication.Uart;

public class Gestion {
	private static Uart uart;
	
	/* ---------------------------------------------------------------------
	 * | 		Définir Temperature
	 * ---------------------------------------------------------------------
	 * | 	|	0 (octet correspondant au Lit) + 3 octets de données
	 * |	|	1 (octet correspondant au Extrudeur 1) + 3 octets de données
	 * |  T	|	2 (octet correspondant au Extrudeur 2) + 3 octets de données
	 * |	|	3 (octet correspondant au Extrudeur 3) + 3 octets de données
	 * |	|	4 (octet correspondant au Extrudeur 4) + 3 octets de données
	 * |	|	5 (octet correspondant au Extrudeur 5) + 3 octets de données
	 * ---------------------------------------------------------------------
	 * |		Demande Température
	 * ---------------------------------------------------------------------
	 * |	|	0 (Lit)
	 * |	|	1 (Air Ambiante)
	 * |	|	2 (Extrudeur 1)
	 * | E	|	3 (Extrudeur 2)
	 * | 	|	4 (Extrudeur 3)
	 * |	|	5 (Extrudeur 4)
	 * |	|	6 (Extrudeur 5)
	 * --------------------------------------------------------------------
	 * |	Demande statuts capteurs de fin de courses
	 * --------------------------------------------------------------------
	 * | S	|	
	 * --------------------------------------------------------------------
	 * 
	 */
	public static void setTemperatureExtrud(int extrudeur, int valeur){
		uart.write("T"+extrudeur+valeur);
	}
	public static String getTemperatureExtrud(){
		uart.write("T1");
		return uart.read();
	}
	public static void setTemperatureBed(int valeur){
		uart.write("T0"+valeur);
	}
	public static String getTemperatureBed(){
		uart.write("T0");
		return uart.read();
	}
	public static void fanOn(){
		uart.write("Allumer les ventilateurs");
	}
	public static void fanOff(){
		uart.write("Eteindre les ventilateurs");
	}
	public static void arretUrgence(){
		uart.write("Arret d'urgence");
	}
	public static int[] askEndStop(){
		uart.write("S");
		String state = uart.read();
		int stateEndStop [] = new int [state.length()];
		int i=0;
		while(i<state.length()){
				stateEndStop[i] = state.charAt(i);
				i++;
		}
		return stateEndStop;
	}
	public static String getTemperatureAir(){
		uart.write("T1");
		return uart.read();
	}
}
