package communication;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import raspberry.Moteur;

import java.io.IOException;

public class PCA9685 {
	public final static int PCA9685_ADDRESS = 0x40;

	public final static int MODE1 = 0x00;//
	public final static int MODE2 = 0x01;// bit 7 à 5 reservés, 
	public final static int SUBADR1 = 0x02;
	public final static int SUBADR2 = 0x03;
	public final static int SUBADR3 = 0x04;
	public final static int DRV1_ON_L = 0x06;
	public final static int DRV1_ON_H = 0x07;
	public final static int DRV1_OFF_L = 0x08;
	public final static int DRV1_OFF_H = 0x09;
	public final static int DRV2_ON_L = 0x0A;
	public final static int DRV2_ON_H = 0x0B;
	public final static int DRV2_OFF_L = 0x0C;
	public final static int DRV2_OFF_H = 0x0D;
	public final static int DRV3_ON_L = 0x0E;
	public final static int DRV3_ON_H = 0x0F;
	public final static int DRV3_OFF_L = 0x10;
	public final static int DRV3_OFF_H = 0x11;
	public final static int DRV4_ON_L = 0x12;
	public final static int DRV4_ON_H = 0x13;
	public final static int DRV4_OFF_L = 0x14;
	public final static int DRV4_OFF_H = 0x15;
	public final static int DRV5_ON_L = 0x16;
	public final static int DRV5_ON_H = 0x17;
	public final static int DRV5_OFF_L = 0x18;
	public final static int DRV5_OFF_H = 0x19;
	public final static int DRV6_ON_L = 0x1A;
	public final static int DRV6_ON_H = 0x1B;
	public final static int DRV6_OFF_L = 0x1C;
	public final static int DRV6_OFF_H = 0x1D;
	public final static int DRV7_ON_L = 0x1E;
	public final static int DRV7_ON_H = 0x1F;
	public final static int DRV7_OFF_L = 0x20;
	public final static int DRV7_OFF_H = 0x21;
	public final static int DRV8_ON_L = 0x22;
	public final static int DRV8_ON_H = 0x23;
	public final static int DRV8_OFF_L = 0x24;
	public final static int DRV8_OFF_H = 0x25;
	public final static int DRV9_ON_L = 0x26;
	public final static int DRV9_ON_H = 0x27;
	public final static int DRV9_OFF_L = 0x28;
	public final static int DRV9_OFF_H = 0x29;
	public final static int DRV10_ON_L = 0x2A;
	public final static int DRV10_ON_H = 0x2B;
	public final static int DRV10_OFF_L = 0x2C;
	public final static int DRV10_OFF_H = 0x2D;
	public final static int DRV11_ON_L = 0x2E;
	public final static int DRV11_ON_H = 0x2F;
	public final static int DRV11_OFF_L = 0x30;
	public final static int DRV11_OFF_H = 0x31;
	public final static int ALL_LED_ON_L = 0xFA;
	public final static int ALL_LED_ON_H = 0xFB;
	public final static int ALL_LED_OFF_L = 0xFC;
	public final static int ALL_LED_OFF_H = 0xFD;
	public final static int PRESCALE = 0xFE;
	public final static int TestMode = 0xFF;

	private static boolean verbose = true;
	private int freq = 60;

	private I2CBus bus;
	private I2CDevice servoDriver;
	public int ValeurPWM;
	 /* Channel  | Axe  
	 * ---------+-----------
	 * 		0 	|   X 
	 * 		1  	|   Y  
	 * 		2  	|   Z
	 * 		3	| 	U
	 * 		4	|	V
	 * 		5	|	W
	 * 		6	|	Extrudeur 1
	 * 		7	|	Extrudeur 2
	 * 		8	|	Extrudeur 3
	 * 		9	|	Extrudeur 4
	 * 		10	|	Extrudeur 5
	 * ---------+----------
	 */
	public int Axe;
	public static int step = 32;// De base  32 µsteps/step
	public static int vitesse;
	private double rayonMoteur = 0.00226, exposant = -3, nombre = 10, resultat = Math.pow( nombre, exposant ); 
	private int denominateur = 60*(int)1.8;

	public PCA9685() throws I2CFactory.UnsupportedBusNumberException {
		this(40); // 0x40 obtained through sudo i2cdetect -y 1
	}

	public PCA9685(int address) throws I2CFactory.UnsupportedBusNumberException {
		try {
			// Get I2C bus
			bus = I2CFactory.getInstance(I2CBus.BUS_1); // Depends onthe RasPI version
			if (verbose) {
				System.out.println("Connected to bus. OK.");
			}

			// Get the device itself
			servoDriver = bus.getDevice(address);
			if (verbose) {
				System.out.println("Connected to device. OK.");
			}
			// Reseting
			servoDriver.write(MODE1, (byte) 0x00);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	public void setFrequence(int frequence){
		this.freq = frequence;
	}
	public int getFrequence(){
		return this.freq;
	}
	
	public void setStep(int step){
		this.step = step;
	}
	public int getStep(){
		return this.step;
	}
	public void setVitesse(int vitesse){
		this.vitesse = (vitesse*100*30)*(int)(Math.PI*rayonMoteur*resultat);
	}
	public int getVitesse(){
		return this.vitesse;
	}
	public void calculFrequenceStep(){
		this.freq = (getVitesse()*getStep()*360)/denominateur; 
	}
	

	/**
	 * @param freq 40..1000
	 */
	public void setPWMFreq(int freq) {
		this.freq = freq;
		float preScaleVal = 25_000_000.0f; // 25MHz
		preScaleVal /= 4_096.0;           // 4096: 12-bit
		preScaleVal /= freq;
		preScaleVal -= 1.0;
		if (verbose) {
			System.out.println("Setting PWM frequency to " + freq + " Hz");
			System.out.println("Estimated pre-scale: " + preScaleVal);
		}
		double preScale = Math.floor(preScaleVal + 0.5);
		if (verbose) {
			System.out.println("Final pre-scale: " + preScale);
		}

		try {
			byte oldmode = (byte) servoDriver.read(MODE1);
			byte newmode = (byte) ((oldmode & 0x7F) | 0x10); // sleep
			servoDriver.write(MODE1, newmode);              // go to sleep
			servoDriver.write(PRESCALE, (byte) (Math.floor(preScale)));
			servoDriver.write(MODE1, oldmode);
			waitfor(5);
			servoDriver.write(MODE1, (byte) (oldmode | 0x80));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * @param channel 0..15
	 * @param on      0..4095 (2^12 positions)
	 * @param off     0..4095 (2^12 positions)
	 */
	public void setPWM(int channel, int on, int off) throws IllegalArgumentException {
		if (channel < 0 || channel > 15) {
			throw new IllegalArgumentException("Channel must be in [0, 15]");
		}
		if (on < 0 || on > 4_095) {
			throw new IllegalArgumentException("On must be in [0, 4095]");
		}
		if (off < 0 || off > 4_095) {
			throw new IllegalArgumentException("Off must be in [0, 4095]");
		}
		if (on > off) {
			throw new IllegalArgumentException("Off must be greater than On");
		}
		try {
			servoDriver.write(DRV1_ON_L + 4 * channel, (byte) (on & 0xFF));
			servoDriver.write(DRV1_ON_H + 4 * channel, (byte) (on >> 8));
			servoDriver.write(DRV1_OFF_L + 4 * channel, (byte) (off & 0xFF));
			servoDriver.write(DRV1_OFF_H + 4 * channel, (byte) (off >> 8));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void waitfor(long howMuch) {
		try {
			Thread.sleep(howMuch);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * @param channel 0..15
	 * @param pulseMS in ms.
	 */
	public void setServoPulse(int channel) {
		double pulseLength = 1_000_000; // 1s = 1,000,000 us per pulse.
		pulseLength /= this.freq;  // 40..1000 Hz
		pulseLength /= 4_096;       // 12 bits of resolution
		int pulse = 1/(2*getFrequence());
		pulse /= pulseLength;
		this.setPWM(getChannel(), 0, pulse);
	}
	
	public int getValeurPWM(){
		return this.ValeurPWM;
	}
	public void setValeurPWM(int valeur){
		this.ValeurPWM = valeur;
	}
	
	public int getChannel(){
		return this.Axe;
	}
	
	public void setChannel(int channel){
		this.Axe = channel;
	}
	
	public void GestionPCA9685(int frequence) throws I2CFactory.UnsupportedBusNumberException{
		this.setPWMFreq(frequence);
		this.setPWM(getChannel(), 0 , ValeurPWM);
		this.setServoPulse(getChannel());
	}
	
	public void StopAxe(){
		for(int i = 0; i < 11; i++){
			this.setPWM(i, 0, 0);
		}
  }
}