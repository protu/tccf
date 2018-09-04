package lan.prov.tr069;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class CWMPMessage {

	private String sessionID = "Initial";
	private static QName qnSoapEncArrayType = new QName("http://schemas.xmlsoap.org/soap/encoding/", "arrayType",
			"soap-enc");
	private static QName qnXsiType = new QName("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi");

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public static QName getQnSoapEncArrayType() {
		return qnSoapEncArrayType;
	}

	public static QName getQnXsiType() {
		return qnXsiType;
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
		envelope.addNamespaceDeclaration("cwmp", "urn:dslforum-org:cwmp-1-2");
//		envelope.addNamespaceDeclaration("cwmp", "urn:dslforum-org:cwmp-1-0");
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

	public SOAPElement parameterValueStruct(String Name, String Value, String Type) throws SOAPException {
		SOAPFactory soapFactory = SOAPFactory.newInstance();
		SOAPElement pvs = soapFactory.createElement("ParameterValueStruct");
		pvs.addChildElement("Name").setValue(Name);
		SOAPElement value = pvs.addChildElement("Value");
		value.addAttribute(getQnXsiType(), Type);
		value.setValue(Value);
		return pvs;
	}

}
