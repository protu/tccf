package lan.prov.srvlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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
			ACSMethods acsMethods = new ACSMethods();
			SOAPMessage soapResponse = null;

			if (cwmpSessionID != "" && session.isNew() && responseType.equals("Inform")) {
				
				String logString = cwmpSessionID + ": " + responseType;
				if (!OUI.isEmpty() || !productClass.isEmpty() || !serialNumber.isEmpty()) {
					logString += " from: \"" + OUI + "-" + productClass + "-" + serialNumber + "\"";
				}
				context.log(logPrefix + logString);

				session.setAttribute("cwmpSessionID", cwmpSessionID);
				session.setAttribute("productClass", productClass);
				session.setAttribute("OUI", OUI);
				session.setAttribute("serialNumber", serialNumber);
				response.setContentType("text/xml; charset=utf-8");
				soapResponse = acsMethods.informResponse(cwmpSessionID);
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
				// Postoji HTTP sesija ali je prazan SOAP sesionID - vraćen je
				// prazan odgovor na inform response i mogu se posaviti
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
				
				response.setContentType("text/xml; charset=utf-8");
				Map<String, String> spvTCList = new HashMap<String, String>();
				// spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.1.SourceIP",
				// "string:10.0.0.0");
				// spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.1.SourceIPMask",
				// "string:255.0.0.0");
				spvTCList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIP", "string:10.0.0.0");
				spvTCList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIPMask", "string:255.0.0.0");
				spvTCList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.4.SourceIP", "string:10.0.0.0");
				spvTCList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.4.SourceIPMask", "string:255.0.0.0");
				spvTCList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIP", "string:10.0.0.0");
				spvTCList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIPMask", "string:255.0.0.0");
				Map<String, String> spvSetACSList = new HashMap<String, String>();
				// spvEltekList.put("InternetGatewayDevice.ManagementServer.URL",
				// "string:http://10.253.47.5:57023/test");
				spvSetACSList.put("InternetGatewayDevice.ManagementServer.URL",
						"string:http://10.243.156.120:7023/Amis/CPEMgt");
				spvSetACSList.put("InternetGatewayDevice.ManagementServer.PeriodicInformInterval",
						"unsignedInt:120");
				if (productClass.equals("SpeedTouch 780")) {
					spvTCList.put("InternetGatewayDevice.ManagementServer.URL",
							"string:http://10.243.156.120:7023/Amis/CPEMgt");
					soapResponse = acsMethods.setParameterValues(spvTCList, cwmpSessionID);
				} else if (productClass.equals("Thomson TG782")) {
					spvTCList.remove("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIP");
					spvTCList.remove("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIPMask");
					spvTCList.remove("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIP");
					spvTCList.remove("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIPMask");
					spvTCList.put("InternetGatewayDevice.ManagementServer.URL",
					"string:http://10.243.156.120:57023/Amis/WGCPEMgt");
					soapResponse = acsMethods.setParameterValues(spvTCList, cwmpSessionID);
				} else if (productClass.equals("MediaAccess TG788vn v2")) {
					spvTCList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.5.SourceIP", "string:10.0.0.0");
					spvTCList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.5.SourceIPMask",
					"string:255.0.0.0");
					spvTCList.put("InternetGatewayDevice.ManagementServer.URL",
					"string:http://10.243.156.120:57023/Amis/WGCPEMgt");
					spvTCList.put("InternetGatewayDevice.ManagementServer.Username", "string:administrator");
					spvTCList.put("InternetGatewayDevice.ManagementServer.Password", "string:EpC71249HgUH16KX9821Lu");
					soapResponse = acsMethods.setParameterValues(spvTCList, cwmpSessionID);
					// Set Encom username and password for ACS
				} else if (productClass.equals("IAD")) {
					spvSetACSList.put("InternetGatewayDevice.ManagementServer.Username", "string:administrator");
					spvSetACSList.put("InternetGatewayDevice.ManagementServer.Password", "string:EpC71249HgUH16KX9821Lu");
					soapResponse = acsMethods.setParameterValues(spvSetACSList, cwmpSessionID);
				} else {
					// Send to all other devices URL for preprovisioned devices 
					soapResponse = acsMethods.setParameterValues(spvSetACSList, cwmpSessionID);
				}
				OutputStream respOut = response.getOutputStream();
				soapResponse.writeTo(respOut);
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
