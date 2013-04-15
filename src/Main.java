import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.java_websocket.WebSocketImpl;

import com.ExXothermic.communication.*;
import com.ExXothermic.message.FactoryMessage;
import com.ExXothermic.message.JSONMessage;
import com.ExXothermic.message.ResponseMessageHandler;
import com.ExXothermic.message.ResponseMessageHandlerUI;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j/log4j.properties");
		Logger  logger = Logger.getLogger("com.ExXothermic.communication");
		try
		{
			WebSocketImpl.DEBUG = false;    

			JSONMessage mess= new JSONMessage();
			 HashMap<String, String> as =new HashMap<String, String>();	
			int port=1025;
			ComunicationManagementForClients server=  ComunicationManagementForClients.getInstance();			
			server.prepareProcess(port,FactoryMessage.getInstance().GetMyBoxCert(),new ResponseMessageHandler() );
			ComunicationManagementForUI ui=  ComunicationManagementForUI.getInstance();			
			ui.prepareProcess(3333,null,new ResponseMessageHandlerUI() );
			Thread thread =new Thread(server);
		    thread.run();
		    Thread thread2 = new Thread(ui);
		    thread2.run();
		}
		catch(Exception ex)
		{

			ex.printStackTrace();
			logger.error(ex);
		}
	}

}
