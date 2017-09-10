package lan.prov.client;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

import lan.prov.tr069.ClientMethods;

public class SoapClient {

	public String SendMessage() {
		
		try { 
		
			ClientMethods clientMethods = new ClientMethods();
			GenericDevice device = new GenericDevice();
			SOAPMessage soapMessage = clientMethods.clientPeriodicInform(device);
			soapMessage.getMimeHeaders().addHeader("SOAPAction", "");
			
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			URL endpoint = new URL("http://localhost:10301/acs/croatia/ULL");
//			URL endpoint = new URL("http://localhost:8080/TccfServer/Acs");
			SOAPMessage response = soapConnection.call(soapMessage, endpoint);
			soapConnection.close();
			
//			MimeHeaders headers = response.getMimeHeaders();
//			while (headers.getAllHeaders().hasNext()) {
//				String header = headers.getAllHeaders().next().toString();
//				System.out.println(header);
//			}
			
			OutputStream output = new ByteArrayOutputStream();
			response.writeTo(output);
			return output.toString();
			
		}
		catch (NullPointerException nex) {
			return "";
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
}
