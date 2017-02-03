package lan.prov.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SoapClient {

	public static void main(String[] main) {
		
		try { 
			
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage soapMessage = factory.createMessage();
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
			id.setValue("Test Client session");

			// SOAP Body

			SOAPBody soapBody = envelope.getBody();
			SOAPElement informResponse = soapBody.addChildElement("InformResponse", "cwmp");
			SOAPElement maxEnvelopes = informResponse.addChildElement("MaxEnvelopes");
			maxEnvelopes.setValue("1");
		
			// Send message
			
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			URL endpoing = new URL("http://localhost:10301/acs/croatia/ULL");
			SOAPMessage response = soapConnection.call(soapMessage, endpoing);
			soapConnection.close();
			
			response.writeTo(System.out);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
}
