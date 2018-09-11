package lan.prov.srvlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
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
			ServletContext context = getServletContext();
			HttpSession session = request.getSession(true);
			String ipAddress = request.getRemoteAddr();
			int remotePort = request.getRemotePort();
			String contSessionID = request.getSession().getId();
			String logPrefix = ipAddress + ":" + String.valueOf(remotePort) + " (in " + contSessionID + "): ";
			// Ukoliko nije postavljeno SOAPAction zaglavlje i ne radi se o praznoj poruci
			// unutar sesije, tada nema smisla nastaviti jer se ne radio o TR069 poruci
			if (request.getHeader("SOAPAction") == null && session.isNew()) {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				context.log(logPrefix + "Not a CWMP request!");
				return;
			}
			DeviceMessageParse deviceMessageParse = new DeviceMessageParse(request);
			String cwmpSessionID = deviceMessageParse.getSessionID();
			String responseType = deviceMessageParse.getResponseType();
			String productClass = deviceMessageParse.getProductClass();
			String OUI = deviceMessageParse.getOUI();
			String serialNumber = deviceMessageParse.getSerialNumber();
			String soapTextMessage = deviceMessageParse.getSOAPTextMessage();
			ACSMethods acsMethods = new ACSMethods();
			SOAPMessage soapResponse = null;

			if (cwmpSessionID != "" && session.isNew() && responseType.equals("Inform")) {

				String logString = cwmpSessionID + ": " + responseType;
				if (!OUI.isEmpty() || !productClass.isEmpty() || !serialNumber.isEmpty()) {
					logString += " from: \"" + OUI + "-" + productClass + "-" + serialNumber + "\"";
				}
				context.log(logPrefix + logString);
				context.log(logPrefix + soapTextMessage);

				session.setAttribute("cwmpSessionID", cwmpSessionID);
				session.setAttribute("productClass", productClass);
				session.setAttribute("OUI", OUI);
				session.setAttribute("serialNumber", serialNumber);
				response.setContentType("text/xml; charset=utf-8");
				soapResponse = acsMethods.informResponse(cwmpSessionID);
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
				// Postoji HTTP sesija ali je prazan SOAP sesionID - vraćen je
				// prazan odgovor na inform response i mogu se postaviti
				// parametri
			} else if (cwmpSessionID == "" && !session.isNew()) {

				cwmpSessionID = (String) session.getAttribute("cwmpSessionID");
				productClass = (String) session.getAttribute("productClass");
				serialNumber = (String) session.getAttribute("serialNumber");
				OUI = (String) session.getAttribute("OUI");

				String logString = cwmpSessionID + ": ";
				if (!OUI.isEmpty() || !productClass.isEmpty() || !serialNumber.isEmpty()) {
					logString += "connect from: \"" + OUI + "-" + productClass + "-" + serialNumber + "\"";
				}
				context.log(logPrefix + logString);
				context.log(logPrefix + soapTextMessage);
				
				response.setContentType("text/xml; charset=utf-8");
				soapResponse = acsMethods.getParameterValues("InternetGatewayDevice.", cwmpSessionID);
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
				// Došao je odgovor modema (Response). Stavi ga u log i prekini sesiju.
			} else if (cwmpSessionID != "" && !session.isNew() && responseType.contains("Response")) {

				cwmpSessionID = (String) session.getAttribute("cwmpSessionID");
				productClass = (String) session.getAttribute("productClass");
				serialNumber = (String) session.getAttribute("serialNumber");
				OUI = (String) session.getAttribute("OUI");

				String logString = cwmpSessionID + ": " + responseType;
				if (!OUI.isEmpty() || !productClass.isEmpty() || !serialNumber.isEmpty()) {
					logString += " from: \"" + OUI + "-" + productClass + "-" + serialNumber + "\"";
				}
				context.log(logPrefix + logString);
				context.log(logPrefix + soapTextMessage);

				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				request.getSession(false);
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
