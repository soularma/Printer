package raspberry;

import communication.Uart;

public class Gestion {
	private static Uart uart;
	
	public static void setTemperatureExtrud(int valeur){
		uart.write("Temperature de l'extrudeur "+valeur);
	}
	public static String getTemperatureExtrud(){
		uart.write("Demande Temperature de l'extrudeur ");
		return uart.read();
	}
	public static void setTemperatureBed(int valeur){
		uart.write("Temperature du lit "+valeur);
	}
	public static String getTemperatureBed(){
		uart.write("Demande Temperature du lit ");
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
		uart.write("statut end stop ");
		String state = uart.read();
		int stateEndStop [] = new int [state.length()];
		for(int i = 0; i<state.length(); i++){
			stateEndStop[i] = state.charAt(i);
		}
		return stateEndStop;
	}
}
