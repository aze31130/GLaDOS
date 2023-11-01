package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	Boolean isConsole;
	
	public Logger(Boolean ic) {
		this.isConsole = ic;
	}
	
	@Override
	public String toString() {
		if(this.isConsole) {
			return new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss] ").format(new Date());
		} else {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		}
	}
}
