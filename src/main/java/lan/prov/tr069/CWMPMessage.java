package lan.prov.tr069;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class CWMPMessage {
	
	String sessionID = "";
	
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public SOAPMessage genericMessage() throws Exception {
		return genericMessage(getSessionID());
	}

	public SOAPMessage genericMessage(String sessionID) throws Exception {

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("cwmp", "urn:dslforum-org:cwmp-1-0");
		envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		envelope.addNamespaceDeclaration("soap-env", "http://schemas.xmlsoap.org/soap/envelope/");
		envelope.addNamespaceDeclaration("soap-enc", "http://schemas.xmlsoap.org/soap/encoding/");

		// SOAP Header
		SOAPHeader header = envelope.getHeader();
		SOAPHeaderElement id = header.addHeaderElement(new QName("urn:dslforum-org:cwmp-1-0", "ID", "cwmp"));
		id.setMustUnderstand(true);
		id.setValue(sessionID);

		return soapMessage;
	}

}
