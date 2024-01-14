package utils;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

public interface Logging {
	Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
}
