package raspberry;

import com.pi4j.io.gpio.*;


public class Moteur{
	final GpioController gpio = GpioFactory.getInstance();
	private int diameter;
	public void setDirection(int MotorNb, int Dir){
		switch(MotorNb){
		case 1:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "DirMoteur1", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "DirMoteur1", PinState.LOW);
				break;
			}break;
		case 2:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, "DirMoteur2", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, "DirMoteur2", PinState.LOW);
				break;
			}break;
		case 3:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "DirMoteur3", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "DirMoteur3", PinState.LOW);
				break;
			}break;
		case 4:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "DirMoteur4", PinState.HIGH);
				break;
			case 0: 
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "DirMoteur4", PinState.LOW);
				break;
			}break;
		case 5:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11, "DirMoteur5", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11, "DirMoteur5", PinState.LOW);
				break;
			}break;
		case 6:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, "DirMoteur6", PinState.HIGH);
			break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, "DirMoteur6", PinState.LOW);
				break;
			}break;
		case 7:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "DirMoteur7", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "DirMoteur7", PinState.LOW);
				break;
			}break;
		case 8:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "DirMoteur8", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "DirMoteur8", PinState.LOW);
				break;
			}break;
		case 9:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "DirMoteur9", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "DirMoteur9", PinState.LOW);
				break;
			}break;
		case 10:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, "DirMoteur10", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, "DirMoteur10", PinState.LOW);
				break;
			}break;
		case 11:
			switch(Dir){
			case 1:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "DirMoteur11", PinState.HIGH);
				break;
			case 0:
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "DirMoteur11", PinState.LOW);
				break;
			}break;
		}
		
	}
	public void setMode(int Mode){
		switch (Mode){
			case 32: //32eme pas
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, "M0", PinState.HIGH);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20, "M1", PinState.HIGH);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "M2", PinState.HIGH);
				break;
			case 16: //16eme pas
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, "M0", PinState.LOW);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20, "M1", PinState.LOW);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "M2", PinState.HIGH);
				break;
			case 8: //8eme pas
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, "M0", PinState.HIGH);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20, "M1", PinState.HIGH);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "M2", PinState.LOW);
				break;
			case 4: //1/4 step (W1-2 phase excitation)
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, "M0", PinState.LOW);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20, "M1", PinState.HIGH);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "M2", PinState.LOW);
				break;
			case 2: //1/2 step (1-2 phase excitation)
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, "M0", PinState.HIGH);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20, "M1", PinState.LOW);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "M2", PinState.LOW);
		        break;
			default: //Full step
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, "M0", PinState.LOW);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20, "M1", PinState.LOW);
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "M2", PinState.LOW);
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
	public void setDiameter(int diameter){
		this.diameter = diameter;
	}
	public int getDiameter(){
		return this.diameter;
	}
}