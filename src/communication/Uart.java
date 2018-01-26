package communication;

import com.pi4j.io.gpio.exception.UnsupportedBoardType;
import com.pi4j.io.serial.*;

import java.io.IOException;

public class Uart{

	// create an instance of the serial communications class
    public Serial serial = SerialFactory.createInstance();
    private String info;
	
    /**
     * Configuration :
     *   "--device (device-path)"                   [DEFAULT: /dev/ttyAMA0]
     *   "--baud (baud-rate)"                       [DEFAULT: 38400]
     *   "--data-bits (5|6|7|8)"                    [DEFAULT: 8]
     *   "--parity (none|odd|even)"                 [DEFAULT: none]
     *   "--stop-bits (1|2)"                        [DEFAULT: 1]
     *   "--flow-control (none|hardware|software)"  [DEFAULT: none]
     *
     * @throws UnsupportedBoardType 
     * @throws InterruptedException
     * @throws IOException
     */
    public Uart() throws UnsupportedBoardType, InterruptedException {

        // print program title/header
        info = ("<-- Raspberry PI -->" + "\n Serial Data Exchange Communication ");

        // create and register the serial data listener

        try {
            // create serial config object
            SerialConfig config = new SerialConfig();

            // set default serial settings (device, baud rate, flow control, etc)
            //
            // by default, use the DEFAULT com port on the Raspberry Pi (exposed on GPIO header)
            // NOTE: this utility method will determine the default serial port for the
            //       detected platform and board/model.  For all Raspberry Pi models
            //       except the 3B, it will return "/dev/ttyAMA0".  For Raspberry Pi
            //       model 3B may return "/dev/ttyS0" or "/dev/ttyAMA0" depending on
            //       environment configuration.
            config.device("/dev/ttyAMA0")
                  .baud(Baud._38400)
                  .dataBits(DataBits._8)
                  .parity(Parity.NONE)
                  .stopBits(StopBits._1)
                  .flowControl(FlowControl.NONE);

            // parse optional command argument options to override the default serial settings.            
                //config = CommandArgumentParser.getSerialConfig(config);
            

            info += config.toString();

            // open the default serial device/port with the configuration settings
            serial.open(config); 
            info += "Connexion Success on : " + config.device();          
        }
        catch(IOException ex) {
            info +=  "==>> SERIAL SETUP FAILED : " + ex.getMessage();
            return;
        }
    }
    
    public void write(String data) throws IllegalStateException, IOException {
    	try{
    		serial.write(data);
    	}catch(IllegalStateException ex) {
    		ex.printStackTrace();
    	}
    }
    
    public String getInfo() {
    	return this.info;
    }
    
}
