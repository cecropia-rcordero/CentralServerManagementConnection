import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ExXothermic.communication.*;
import com.ExXothermic.message.JSONMessage;
import com.ExXothermic.message.ResponseMessageHandler;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j/log4j.properties");
		Logger  logger = Logger.getLogger("com.ExXothermic.communication");
		try
		{

			JSONMessage mess= new JSONMessage();
			 HashMap<String, String> as =new HashMap<String, String>();

			
			
			int port=1025;
			ComunicationManagement server=  ComunicationManagement.getInstance();			
			server.startProcess(port,new ResponseMessageHandler() );
		}
		catch(Exception ex)
		{

			ex.printStackTrace();
			logger.error(ex);
		}
	}

}
