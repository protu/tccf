package lan.prov.tr069;

import javax.xml.soap.SOAPMessage;
import lan.prov.tr069.CWMPMessage;

public class ClientMethods extends CWMPMessage {
	
	public SOAPMessage ClientInform() throws Exception {
		
		SOAPMessage informMessage = genericMessage();
		return informMessage;
	}

}
