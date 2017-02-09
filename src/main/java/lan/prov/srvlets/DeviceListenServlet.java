package lan.prov.srvlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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
			String sessionID = deviceMessageParse.getSessionID();
			String responseType = deviceMessageParse.getResponseType();
			HttpSession session = request.getSession(true);
			ACSMethods acsMethods = new ACSMethods();

			// Prazan HTTP zahtjev i nova sesija - nije TR069 - odgovori sa 400
			if (sessionID == "" && session.isNew()) {
				response.setContentType("text/html); charset=utf-8");
				response.setStatus(HttpStatus.BAD_REQUEST_400);
				request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				// Uredjaj je otvorio novu sesiju sa inform porukom
			} else if (sessionID != "" && session.isNew() && responseType.equals("Inform")) {
				session.setAttribute("cwmpSessionID", sessionID);
				response.setContentType("text/xml; charset=utf-8");
				SOAPMessage soapResponse = acsMethods.informResponse(sessionID);
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
				// Postoji HTTP sesija ali je prazan SOAP sesionID - vraćen je
				// prazan odgovor na inform response i mogu se posaviti parametri
			} else if (sessionID == "" && !session.isNew()) {
				sessionID = (String) session.getAttribute("cwmpSessionID");
				response.setContentType("text/xml; charset=utf-8");
				Map<String, String> spvList = new HashMap<String, String>();
//				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.1.SourceIP", "10.0.0.0");
//				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.1.SourceIPMask", "255.0.0.0");
				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIP", "10.0.0.0");
				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIPMask", "255.0.0.0");
				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.4.SourceIP", "10.0.0.0");
				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.4.SourceIPMask", "255.0.0.0");
				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.5.SourceIP", "10.0.0.0");
				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.5.SourceIPMask", "255.0.0.0");
				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIP", "10.0.0.0");
				spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIPMask", "255.0.0.0");
				spvList.put("InternetGatewayDevice.ManagementServer.URL", "http://10.253.47.5:57003/cwmpWeb/WGCPEMgt");
				SOAPMessage soapResponse = acsMethods.setParameterValues(spvList, sessionID);
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
//				// Parametri su postavljeni, trazimo nove podatke
//			} else if (sessionID != "" && !session.isNew() && responseType.equals("SetParameterValuesResponse")) {
//				sessionID = (String) session.getAttribute("cwmpSessionID");
//				response.setContentType("text/xml; charset=utf-8");
//				SOAPMessage soapResponse = acsMethods
//						.getParameterValues("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.", sessionID);
//				OutputStream respOut = response.getOutputStream();
//				soapResponse.writeTo(respOut);
//				// Podaci poslani, zavrsi sesiju
//			} else if (sessionID != "" && !session.isNew() && responseType.equals("GetParameterValuesResponse")) {
			} else if (sessionID != "" && !session.isNew() && responseType.equals("SetParameterValuesResponse")) {
				response.setStatus(HttpStatus.OK_200);
				request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
			} else {
				response.setStatus(HttpStatus.BAD_REQUEST_400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
