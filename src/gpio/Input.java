package gpio;


import com.pi4j.io.gpio.*;

import com.pi4j.util.Console;

public class Input {
	
	final GpioController gpio = GpioFactory.getInstance(); 
	
	public Input() {
		final Console console = new Console();
		console.promptForExit();
		console.title("<-- Rasperry Pi --> Input GPIO");
		
		

	}
	

}
