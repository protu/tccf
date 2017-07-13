package lan.prov.tr069;

import java.util.HashMap;
import java.util.Map;

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

	String sessionID = "";
	QName qnSoapEncArrayType = new QName("http://schemas.xmlsoap.org/soap/encoding/", "array-Type", "SOAP-ENC");
	QName qnXsiType = new QName("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi");

	public ACSMethods() {
	};

	public ACSMethods(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	private SOAPMessage genericMessage() throws Exception {
		return genericMessage(getSessionID());
	}

	private SOAPMessage genericMessage(String sessionID) throws Exception {

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

		return soapMessage;
	}

	public SOAPMessage informResponse() throws Exception {
		return informResponse(getSessionID());
	}

	public SOAPMessage informResponse(String sessionID) throws Exception {

		SOAPMessage soapMessage = genericMessage(sessionID);
		SOAPBody soapBody = soapMessage.getSOAPBody();
		SOAPElement informResponse = soapBody.addChildElement("InformResponse", "cwmp");
		SOAPElement maxEnvelopes = informResponse.addChildElement("MaxEnvelopes");
		maxEnvelopes.setValue("1");

		return soapMessage;

	}

	public SOAPMessage getParameterValues(String parameter) throws Exception {
		return getParameterValues(parameter, getSessionID());
	}

	public SOAPMessage getParameterValues(String parameter, String sessionID) throws Exception {

		SOAPMessage soapMessage = genericMessage(sessionID);
		SOAPBody soapBody = soapMessage.getSOAPBody();
		SOAPElement gpv = soapBody.addChildElement("GetParameterValues", "cwmp");
		SOAPElement parameterValues = gpv.addChildElement("ParameterValues");
		parameterValues.addAttribute(qnSoapEncArrayType, "xsd:string[1]");
		parameterValues.addChildElement("string").setValue(parameter);

		return soapMessage;
	}

	public SOAPMessage setParameterValues(String parameter, String value) throws Exception {
		return setParameterValues(parameter, value, getSessionID());
	}

	public SOAPMessage setParameterValues(String parameter, String value, String sessionID) throws Exception {

		Map<String, String> parameterList = new HashMap<String, String>();
		parameterList.put(parameter, value);
		
		return setParameterValues(parameterList, sessionID);
	}

	public SOAPMessage setParameterValues(Map<String, String> parameterList, String sessionID) throws Exception {

		SOAPMessage soapMessage = genericMessage(sessionID);
		SOAPBody soapBody = soapMessage.getSOAPBody();
		SOAPElement spv = soapBody.addChildElement("SetParameterValues", "cwmp");
		SOAPElement seParameterList = spv.addChildElement("ParameterList");
		
		String parameterListSize = String.valueOf(parameterList.size());
		seParameterList.addAttribute(qnSoapEncArrayType, "xsd:string[" + parameterListSize + "]");
		
		for (String parameter : parameterList.keySet()) {
			SOAPElement seParameterValueStruct = seParameterList.addChildElement("ParameterValueStruct");
			seParameterValueStruct.addChildElement("Name").setValue(parameter);
			SOAPElement sePvsValue = seParameterValueStruct.addChildElement("Value");
			sePvsValue.setValue(parameterList.get(parameter));
			sePvsValue.addAttribute(qnXsiType, "xsd:string");
		}

		return soapMessage;
	}

	public SOAPMessage getParameterNames(String parameter) throws Exception {
		return getParameterNames(parameter, getSessionID());
	}

	public SOAPMessage getParameterNames(String parameter, String sessionID) throws Exception {

		SOAPMessage soapMessage = genericMessage(sessionID);
		SOAPBody soapBody = soapMessage.getSOAPBody();
		SOAPElement gpv = soapBody.addChildElement("GetParameterNames", "cwmp");
		gpv.addChildElement("ParameterPath").setValue(parameter);
		gpv.addChildElement("NextLevel").setValue("true");

		return soapMessage;
	}
	
	public SOAPMessage reboot(String sessionID) throws Exception {
		SOAPMessage soapMessage = genericMessage(sessionID);
		SOAPBody soapBody = soapMessage.getSOAPBody();
		SOAPElement rbt = soapBody.addChildElement("Reboot", "cwmp");
		rbt.addChildElement("CommandKey").setValue("brb");
		
		return soapMessage;
	}
	
	
}
