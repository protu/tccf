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

		try {
			DeviceMessageParse deviceMessageParse = new DeviceMessageParse(request);
			ACSMethods acsMethods = new ACSMethods();
			String sessionID = deviceMessageParse.getSessionID();
			HttpSession session = request.getSession(true);
			if (sessionID == "" && session.isNew()) {
				response.setContentType("text/html); charset=utf-8");
				response.getWriter().write(sessionID);
			} else if (sessionID == "") {
				sessionID = (String) session.getAttribute("cwmpSessionID");
			} else {
				session.setAttribute("cwmpSessionID", sessionID);
				response.setContentType("text/xml; charset=utf-8");
				SOAPMessage soapResponse = acsMethods.informResponse(sessionID);
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
