package gpio;


import com.pi4j.io.gpio.*;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import com.pi4j.util.ConsoleColor;

import java.util.ArrayList;
import java.util.List;

public class Input {
	
	final GpioController gpio = GpioFactory.getInstance(); 
	
	public Input() {
		final Console console = new Console();
		console.promptForExit();
		console.title("<-- Rasperry Pi --> Input GPIO");
		
		

	}
	

}
