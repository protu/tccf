package lan.prov.tr069;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class ACSMethods {

	public SOAPMessage getDeviceDetails() throws Exception {
		return informResponse("SessionID");
	}
	
	public SOAPMessage informResponse(String sessionID) throws Exception {

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("cwmp", "urn:dslforum-org:cwmp-1-0");
		envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
	    envelope.addNamespaceDeclaration("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
	    envelope.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");
		
		// SOAP Header
		
		SOAPHeader header = envelope.getHeader();
		SOAPHeaderElement id = header.addHeaderElement(new QName("urn:dslforum-org:cwmp-1-0", "ID", "cwmp"));
		id.setMustUnderstand(true);
		id.setValue(sessionID);

		// SOAP Body

		SOAPBody soapBody = envelope.getBody();
		SOAPElement informResponse = soapBody.addChildElement("InformResponse", "cwmp");
		SOAPElement maxEnvelopes = informResponse.addChildElement("MaxEnvelopes");
		maxEnvelopes.setValue("1");
	
		soapMessage.saveChanges();
		return soapMessage;

	}
	
	public SOAPMessage getParameterValues(String parameter, String sessionID) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("cwmp", "urn:dslforum-org:cwmp-1-0");
		envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
	    envelope.addNamespaceDeclaration("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
	    envelope.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");
		
		// SOAP Header
		
		SOAPHeader header = envelope.getHeader();
		SOAPHeaderElement id = header.addHeaderElement(new QName("urn:dslforum-org:cwmp-1-0", "ID", "cwmp"));
		id.setMustUnderstand(true);
		id.setValue(sessionID);

		// SOAP Body

		SOAPBody soapBody = envelope.getBody();
		SOAPElement gpv = soapBody.addChildElement("GetParameterValues", "cwmp");
		SOAPElement parameterValues = gpv.addChildElement("ParameterValues");
		parameterValues.addNamespaceDeclaration("arrayType", "xsd:string[1]");
		parameterValues.addChildElement("string").setValue(parameter);		
		
		return soapMessage;
	}
	
	public SOAPMessage getParameterNames(String parameter, String sessionID) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("cwmp", "urn:dslforum-org:cwmp-1-0");
		envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
	    envelope.addNamespaceDeclaration("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
	    envelope.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");
		
		// SOAP Header
		
		SOAPHeader header = envelope.getHeader();
		SOAPHeaderElement id = header.addHeaderElement(new QName("urn:dslforum-org:cwmp-1-0", "ID", "cwmp"));
		id.setMustUnderstand(true);
		id.setValue(sessionID);

		// SOAP Body

		SOAPBody soapBody = envelope.getBody();
		SOAPElement gpv = soapBody.addChildElement("GetParameterNames", "cwmp");
		gpv.addChildElement("ParameterPath").setValue(parameter);
		gpv.addChildElement("NextLevel").setValue("true");
		
		return soapMessage;
	}
}
