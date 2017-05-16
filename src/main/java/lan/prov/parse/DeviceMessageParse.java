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
import org.w3c.dom.NodeList;

public class DeviceMessageParse {
	
	private HttpServletRequest request;
	private String responseType = "";
	private String productClass = "";
	private String OUI = "";

	public DeviceMessageParse(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getSessionID() {
		return getSessionID(request);
	}
	
	public String getResponseType() {
		return responseType;
	}

	public String getProductClass() {
		return productClass;
	}


	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}

	public String getOUI() {
		return OUI;
	}
	
	public void setOUI(String oUI) {
		OUI = oUI;
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
			if (responseType.equals("Inform")) {
				NodeList nlProductClass = soapBody.getElementsByTagName("ProductClass");
				Node nProductclass = nlProductClass.item(0);
				String sProductClass = nProductclass != null ? nProductclass.getTextContent() : "";
				setProductClass(sProductClass);
				NodeList nlOUI = soapBody.getElementsByTagName("OUI");
				Node nOUI = nlOUI.item(0);
				String sOUI = nOUI != null ? nOUI.getTextContent() : "";
				setOUI(sOUI);
			}
			return sessionID;
		} catch (SOAPException e) {
			return "";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
