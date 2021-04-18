package aze.GLaDOS.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private String time = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]").format(new Date());
	@Override
	public String toString() {
		return this.time;
	}
}
