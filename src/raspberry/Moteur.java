package raspberry;

import communication.I2C;
import com.pi4j.io.gpio.*;


public class Moteur{
	final GpioController gpio = GpioFactory.getInstance();

	public void setDirection(int MotorNb, int Dir){
		final GpioPinDigitalOutput pin_Dir1, pin_Dir2, pin_Dir3, pin_Dir4, pin_Dir5, pin_Dir6, pin_Dir7, pin_Dir8, pin_Dir9, pin_Dir10, pin_Dir11 ;
		switch(MotorNb){
		case 1:
			switch(Dir){
			case 1:
				pin_Dir1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, "DirMoteur1", PinState.HIGH);
				break;
			case 0:
				pin_Dir1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, "DirMoteur1", PinState.LOW);
				break;
			}break;
		case 2:
			switch(Dir){
			case 1:
				pin_Dir2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "DirMoteur2", PinState.HIGH);
				break;
			case 0:
				pin_Dir2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "DirMoteur2", PinState.LOW);
				break;
			}break;
		case 3:
			switch(Dir){
			case 1:
				pin_Dir3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "DirMoteur3", PinState.HIGH);
				break;
			case 0:
				pin_Dir3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "DirMoteur3", PinState.LOW);
				break;
			}break;
		case 4:
			switch(Dir){
			case 1:
				pin_Dir4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11, "DirMoteur4", PinState.HIGH);
				break;
			case 0: 
				pin_Dir4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11, "DirMoteur4", PinState.LOW);
				break;
			}break;
		case 5:
			switch(Dir){
			case 1:
				pin_Dir5 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, "DirMoteur5", PinState.HIGH);
				break;
			case 0:
				pin_Dir5 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, "DirMoteur5", PinState.LOW);
				break;
			}break;
		case 6:
			switch(Dir){
			case 1:
				pin_Dir6 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "DirMoteur6", PinState.HIGH);
			break;
			case 0:
				pin_Dir6 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "DirMoteur6", PinState.LOW);
				break;
			}break;
		case 7:
			switch(Dir){
			case 1:
				pin_Dir7 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "DirMoteur7", PinState.HIGH);
				break;
			case 0:
				pin_Dir7 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "DirMoteur7", PinState.LOW);
				break;
			}break;
		case 8:
			switch(Dir){
			case 1:
				pin_Dir8 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "DirMoteur8", PinState.HIGH);
				break;
			case 0:
				pin_Dir8 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "DirMoteur8", PinState.LOW);
				break;
			}break;
		case 9:
			switch(Dir){
			case 1:
				pin_Dir9 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "DirMoteur9", PinState.HIGH);
				break;
			case 0:
				pin_Dir9 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "DirMoteur9", PinState.LOW);
				break;
			}break;
		case 10:
			switch(Dir){
			case 1:
				pin_Dir10 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, "DirMoteur10", PinState.HIGH);
				break;
			case 0:
				pin_Dir10 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, "DirMoteur10", PinState.LOW);
				break;
			}break;
		case 11:
			switch(Dir){
			case 1:
				pin_Dir11 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "DirMoteur11", PinState.HIGH);
				break;
			case 0:
				pin_Dir11 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "DirMoteur11", PinState.LOW);
				break;
			}break;
		}
		
	}
	public void setMode(int Mode){
		final GpioPinDigitalOutput pin_M0, pin_M1, pin_M2;
		switch (Mode){
			case 32: //32eme pas
		        pin_M0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "M0", PinState.HIGH);
		        pin_M1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "M1", PinState.HIGH);
		        pin_M2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, "M2", PinState.HIGH);
				break;
			case 16: //16eme pas
				pin_M0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "M0", PinState.LOW);
		        pin_M1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "M1", PinState.LOW);
		        pin_M2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, "M2", PinState.HIGH);
				break;
			case 8: //8eme pas
				pin_M0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "M0", PinState.HIGH);
		        pin_M1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "M1", PinState.HIGH);
		        pin_M2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, "M2", PinState.LOW);
				break;
			case 4: //1/4 step (W1-2 phase excitation)
				pin_M0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "M0", PinState.LOW);
		        pin_M1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "M1", PinState.HIGH);
		        pin_M2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, "M2", PinState.LOW);
				break;
			case 2: //1/2 step (1-2 phase excitation)
				pin_M0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "M0", PinState.HIGH);
		        pin_M1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "M1", PinState.LOW);
		        pin_M2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, "M2", PinState.LOW);
		        break;
			default: //Full step
				pin_M0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "M0", PinState.LOW);
		        pin_M1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "M1", PinState.LOW);
		        pin_M2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, "M2", PinState.LOW);
		}
	}
	public void setCadence(int Decay){
		
	}
	public void setHome(){
		setMode(8);
		setDirection(1,1);
		setDirection(2,1);
		setDirection(3,1);
		setDirection(4,1);
		setDirection(5,1);
		setDirection(6,1);
	}
}