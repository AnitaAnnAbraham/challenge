package haufe.challenge.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggingUtil {

	static final Logger logger = Logger.getLogger(LoggingUtil.class);
	
	public static void loadLogger() {
		File log4jfile = new File("./src/main/resources/log4j.properties");
		PropertyConfigurator.configure(log4jfile.getAbsolutePath());
	}
	
	public static Logger getLogger() {
		if(logger != null) {
			loadLogger();
			return logger;
		}
		else {
			loadLogger();
			return logger;
		}
	}
}
