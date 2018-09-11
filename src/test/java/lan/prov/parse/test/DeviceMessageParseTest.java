package lan.prov.parse.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.soap.SOAPMessage;

import org.junit.Before;
import org.junit.Test;

import lan.prov.client.GenericDevice;
import lan.prov.parse.DeviceMessageParse;
import lan.prov.tr069.ClientMethods;

public class DeviceMessageParseTest {

	DeviceMessageParse parseMsg;
	SOAPMessage soapMessage = null;
	
	@Before
	public void startParser() {
		ClientMethods clientMethods = new ClientMethods();
		GenericDevice device = new GenericDevice();
		try {
			soapMessage = clientMethods.clientPeriodicInform(device);
			parseMsg = new DeviceMessageParse(soapMessage);
			OutputStream output = new ByteArrayOutputStream();
			soapMessage.writeTo(output);
			System.out.println(output.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSessionIDSoapMessage() {
			String sessionID = parseMsg.getSessionID();
			assertEquals("Initial", sessionID);
	}
	
	@Test
	public void testGetSerialNUmber() {
		String serialNumber = parseMsg.getSerialNumber();
		assertEquals("01DA41AA0001", serialNumber);
	}
	
	@Test
	public void testGetProductClass() {
		String productClass = parseMsg.getProductClass();
		assertEquals("Soft Device", productClass);
	}

}
