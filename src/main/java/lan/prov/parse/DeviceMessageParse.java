package lan.prov.parse;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;

public class DeviceMessageParse {
	
	private HttpServletRequest request;
	private String responseType = "";

	public DeviceMessageParse(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getSessionID() {
		return getSessionID(request);
	}
	
	public String getResponseType() {
		return responseType;
	}

	public String getSessionID(HttpServletRequest request) {
		
		try {
			MessageFactory msgFact = MessageFactory.newInstance();
			InputStream inStream = request.getInputStream();
			if (inStream.available() == 0) {
				return "";
			}
			SOAPMessage soapMessage = msgFact.createMessage(new MimeHeaders(), inStream);
			SOAPHeader soapHead = soapMessage.getSOAPHeader();
			String sessionID = soapHead.getTextContent();
			SOAPBody soapBody = soapMessage.getSOAPBody();
			Node element = soapBody.getFirstChild();
			responseType = element.getNextSibling().getLocalName();
			return sessionID;
		} catch (SOAPException e) {
			return "";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
