package lan.prov.srvlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPMessage;

import lan.prov.parse.DeviceMessageParse;
import lan.prov.tr069.ACSMethods;

public class DeviceListenServlet extends HttpServlet {

	
	private static final long serialVersionUID = -7084727993099036443L;

	public String getPostData(HttpServletRequest request) throws IOException {
	    StringBuilder sb = new StringBuilder();
	        BufferedReader reader = request.getReader();
	        reader.mark(10000);

	        String line;
	        do {
	            line = reader.readLine();
	            sb.append(line).append("\n");
	        } while (line != null);
	        reader.reset();
	        // do NOT close the reader here, or you won't be able to get the post data twice

	    return sb.toString();
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DeviceMessageParse deviceMessageParse = new DeviceMessageParse(request);
		ACSMethods acsMethods = new ACSMethods();
		response.setContentType("text/xml; charset=utf-8");
		try {
			String sessionID = deviceMessageParse.getSessionID();
			SOAPMessage soapResponse = acsMethods.informResponse(sessionID);
			OutputStream respOut = response.getOutputStream();
			soapResponse.writeTo(respOut);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	
}
