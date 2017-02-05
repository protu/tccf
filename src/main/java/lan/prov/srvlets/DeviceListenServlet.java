package lan.prov.srvlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.soap.SOAPMessage;

import lan.prov.parse.DeviceMessageParse;
import lan.prov.tr069.ACSMethods;

public class DeviceListenServlet extends HttpServlet {

	private static final long serialVersionUID = -7084727993099036443L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession(true);
		int contentLength = request.getContentLength();
		if (contentLength < 1) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("");
			return;
		}
		try {
			DeviceMessageParse deviceMessageParse = new DeviceMessageParse(request);
			ACSMethods acsMethods = new ACSMethods();
			response.setContentType("text/xml; charset=utf-8");
			String sessionID = deviceMessageParse.getSessionID();
			SOAPMessage soapResponse = acsMethods.informResponse(sessionID);
			OutputStream respOut = response.getOutputStream();
			soapResponse.writeTo(respOut);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
