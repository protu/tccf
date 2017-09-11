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

	private SOAPMessage soapMessage;
	private String responseType = "";
	private String productClass = "";
	private String OUI = "";
	private String SerialNumber = "";
	private String sessionID = "";

	public DeviceMessageParse() {
	}

	public DeviceMessageParse(HttpServletRequest request) {
		this.soapMessage = getSOAPMessage(request);
	}

	public String getSessionID() {
		if (sessionID == "") {
			sessionID = parseSOAPMessage(soapMessage);
		}
		return sessionID;
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

	public String getSerialNumber() {
		return SerialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}

	private SOAPMessage getSOAPMessage(HttpServletRequest request) {

		try {
			MessageFactory msgFact = MessageFactory.newInstance();
			InputStream inStream = request.getInputStream();
			if (inStream.available() == 0) {
				return null;
			}
			SOAPMessage soapMessage = msgFact.createMessage(new MimeHeaders(), inStream);
			return soapMessage;
		} catch (IOException | SOAPException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getSessionID(SOAPMessage soapMessage) {
			if (soapMessage == null) {
				return "";
			}
			try {
			SOAPHeader soapHead = soapMessage.getSOAPHeader();
			String sessionID = soapHead.getTextContent();
			return sessionID;
		} catch (SOAPException ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	public String parseSOAPMessage(SOAPMessage soapMessage) {
		if (soapMessage == null) {
			return "";
		}
		try {
			SOAPHeader soapHead = soapMessage.getSOAPHeader();
			sessionID = soapHead.getTextContent();
			SOAPBody soapBody = soapMessage.getSOAPBody();
			Node element = soapBody.getFirstChild();
			if (element.getNextSibling() != null) {
				responseType = element.getNextSibling().getLocalName();
			} else {
				return "";
			}
			if (responseType.equals("Inform")) {
				NodeList nlProductClass = soapBody.getElementsByTagName("ProductClass");
				Node nProductclass = nlProductClass.item(0);
				String sProductClass = nProductclass != null ? nProductclass.getTextContent() : "";
				setProductClass(sProductClass);
				NodeList nlOUI = soapBody.getElementsByTagName("OUI");
				Node nOUI = nlOUI.item(0);
				String sOUI = nOUI != null ? nOUI.getTextContent() : "";
				setOUI(sOUI);
				NodeList nlSerialNumber = soapBody.getElementsByTagName("SerialNumber");
				Node nSerialNumber = nlSerialNumber.item(0);
				String sSerialNumber = nSerialNumber != null ? nSerialNumber.getTextContent() : "";
				setSerialNumber(sSerialNumber);
			}
			return sessionID;
		} catch (SOAPException ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
