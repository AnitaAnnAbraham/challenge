package haufe.challenge.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	private Properties prop;

	/**
	 * This method is used to load the properties from config.properties file
	 * @return it returns Properties prop object
	 * @throws IOException 
	 */
	
	public Properties init_prop() {

		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			LoggingUtil.getLogger().error("FileNotFoundException occured at " +e.getMessage());
		} catch (IOException e) {
			LoggingUtil.getLogger().error("IOException occured at " +e.getMessage());
		}

		return prop;

	}

}
