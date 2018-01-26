package raspberry;

import java.io.IOException;

import com.pi4j.io.gpio.exception.UnsupportedBoardType;

import communication.Uart;

public class Gestion {

	public void main(String args[]) throws UnsupportedBoardType, IOException, InterruptedException {

		@SuppressWarnings("unused")
		Uart uart = new Uart();
	}
}
