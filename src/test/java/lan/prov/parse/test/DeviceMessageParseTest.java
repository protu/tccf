package lan.prov.parse.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.soap.SOAPMessage;

import org.junit.Test;

import lan.prov.client.GenericDevice;
import lan.prov.parse.DeviceMessageParse;
import lan.prov.tr069.ClientMethods;

public class DeviceMessageParseTest {

	DeviceMessageParse parseMsg = new DeviceMessageParse();


	@Test
	public void testGetSessionIDSoapMessage() {
		ClientMethods clientMethods = new ClientMethods();
		GenericDevice device = new GenericDevice();
		try {
			SOAPMessage soapMessage = clientMethods.clientPeriodicInform(device);
			OutputStream output = new ByteArrayOutputStream();
			soapMessage.writeTo(output);
//			System.out.println(output.toString());
			String sessionID = parseMsg.getSessionID(soapMessage);
			assertEquals("Initial", sessionID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
