package communication;

import com.pi4j.io.gpio.exception.UnsupportedBoardType;
import com.pi4j.io.serial.*;
import com.pi4j.util.Console;

import java.io.IOException;

/* Classe de gestion de l'Uart avec le PIC18F45k20*/

public class Uart{
	String dataRead = "";
	final Console console = new Console();
	final Serial serial = SerialFactory.createInstance();
	
	public Uart() throws UnsupportedBoardType, IOException, InterruptedException {
		
		console.title("<-- Raspberry pi --> Serial Communication  ");
		console.promptForExit();
		
		try {
		  SerialConfig config = new SerialConfig();
		  config.device(SerialPort.getDefaultPort())
				.baud(Baud._230400)
				.dataBits(DataBits._8)
				.flowControl(FlowControl.NONE)
				.parity(Parity.NONE)
				.stopBits(StopBits._1);
		  console.box("Connection to" + config.toString() );

		  serial.open(config);
		 	  
		}catch(IllegalStateException ex ) {
			ex.printStackTrace();	
			console.print("Error config UART");
		}
	}
	
	public void write(String data) {
		if(serial.isOpen()) {
			try {
				serial.write(data);
				//serial.write(new Date().toString());
				System.out.println(data);
			}catch(IOException e){
				console.print("Unable to write data : " + e.getMessage());
			}
		}
	}
	
	public String read() {
		if(serial.isOpen()) {
			serial.addListener(new SerialDataEventListener() {
				public void dataReceived(SerialDataEvent event) {
					try {
						dataRead = event.getAsciiString();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			});
		}
	return dataRead;
	}
	
	
}
