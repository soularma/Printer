package communication;

import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.util.Console;

/*classe de gestion de l'I2C*/

public class I2C {

	final Console console = new Console();
	final I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
	private byte deviceAddress = (byte)0x00;	
	@SuppressWarnings("unused")
	private byte controlRegister = (byte)0x00;
	@SuppressWarnings("unused")
	private byte registerID = (byte)0x00;
	
	
	public I2C(byte deviceAddress, byte registerID) throws InterruptedException, PlatformAlreadyAssignedException, IOException, UnsupportedBusNumberException {
 
		int deviceID;
		
		this.deviceAddress = deviceAddress;
		this.registerID = registerID;
		
		console.title("<-- Raspberry Pi --> I2C Communication");
		console.promptForExit();
		
		I2CDevice device = i2c.getDevice(deviceAddress);
		deviceID = device.read(registerID);
		
		console.print("Reading ID from device... --> " + String.format("0x%02x" , deviceID ));
		
	}
	
	public void setDeviceAddress(byte address) {
		this.deviceAddress = address;
	}
	
	public void setControlRegister(byte controlRegister) {
		this.controlRegister = controlRegister;
	}
	
	public int read(byte register) throws IOException {
		int data = 0;
		try {
			data = i2c.getDevice(this.deviceAddress).read(register);

		}catch(IOException e) {
			e.printStackTrace();
			console.print("Unable to read data !!!");
		}
		return data;
	}
	
	public void write(byte data) throws IOException {
		try {
			i2c.getDevice(deviceAddress).write(data);
		}catch(IOException e) {
			e.printStackTrace();
			console.print("Unable to write -->" + data);
		}
	}
	public void write(byte[] data) throws IOException {
		try {
			i2c.getDevice(deviceAddress).write(data);
		}catch(IOException e) {
			e.printStackTrace();	
			console.print("Unable to write -->" + data);
	}
}

}
