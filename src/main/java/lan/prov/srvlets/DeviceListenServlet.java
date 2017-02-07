package lan.prov.srvlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.soap.SOAPMessage;

import org.eclipse.jetty.http.HttpStatus;

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
			String responseType = deviceMessageParse.getResponseType();
			HttpSession session = request.getSession(true);

			// Prazan HTTP zahtjev i nova sesija - nije TR069 - odgovori sa 400
			if (sessionID == "" && session.isNew()) {
				response.setContentType("text/html); charset=utf-8");
				response.setStatus(HttpStatus.BAD_REQUEST_400);
				request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				// Postoji HTTP sesija ali je prazan SOAP sesionID - vraćen je
				// prazan odgovor i moze se postaviti upit
			} else if (sessionID == "" && !session.isNew()) {
				sessionID = (String) session.getAttribute("cwmpSessionID");
				session.setAttribute("cwmpSessionID", sessionID);
				response.setContentType("text/xml; charset=utf-8");
				SOAPMessage soapResponse = acsMethods.getParameterNames("InternetGatewayDevice.X_000E50_Firewall.",
						sessionID);
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
				// Inicijalni inform ima sessionID u zahtjevu ali nije kreirana
				// HTTP sesija. Potrebno je vratiti inform response
			} else if (sessionID != "" && session.isNew()) {
				session.setAttribute("cwmpSessionID", sessionID);
				response.setContentType("text/xml; charset=utf-8");
				SOAPMessage soapResponse = acsMethods.informResponse(sessionID);
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
			} else if (sessionID != "" && !session.isNew() && responseType != "") {
				response.setStatus(HttpStatus.OK_200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
