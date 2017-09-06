package lan.prov.tr069.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.junit.Test;

import lan.prov.tr069.ACSMethods;

public class ACSMethodsTest {

	private ACSMethods acsMethods = new ACSMethods();

	private Map<String, String> getSPVList() {
		Map<String, String> spvList = new HashMap<String, String>();
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.1.SourceIP", "string:10.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.1.SourceIPMask", "string:255.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIP", "string:10.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIPMask", "string:255.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.4.SourceIP", "string:10.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.4.SourceIPMask", "string:255.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.5.SourceIP", "string:10.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.5.SourceIPMask", "string:255.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIP", "string:10.0.0.0");
//		spvList.put("InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIPMask", "string:255.0.0.0");
		spvList.put("InternetGatewayDevice.ManagementServer.URL", "string:​​http://10.243.156.120:57023/Amis/WGCPEMgt");
		spvList.put("InternetGatewayDevice.ManagementServer.URL", "string:http://10.243.156.120:57023/Amis/WGCPEMgt");
//		spvList.put("InternetGatewayDevice.ManagementServer.Username", "string:administrator");
//		spvList.put("InternetGatewayDevice.ManagementServer.Password", "string:EpC71249HgUH16KX9821Lu");

		return spvList;
	}

	@Test
	public void SPVMethodTest() throws Exception {
		SOAPMessage spvMessage = acsMethods.setParameterValues(getSPVList(), "test-session");
		String message = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\" "
				+ "xmlns:soap-enc=\"http://schemas.xmlsoap.org/soap/encoding/\" "
				+ "xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
				+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<SOAP-ENV:Header>"
				+ "<cwmp:ID SOAP-ENV:mustUnderstand=\"1\">test-session</cwmp:ID>"
				+ "</SOAP-ENV:Header>"
				+ "<SOAP-ENV:Body>"
				+ "<cwmp:SetParameterValues>"
				+ "<ParameterList soap-enc:arrayType=\"cwmp:ParameterValueStruct[13]\">"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.1.SourceIPMask</Name>"
				+ "<Value xsi:type=\"xsd:string\">255.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.5.SourceIPMask</Name>"
				+ "<Value xsi:type=\"xsd:string\">255.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIPMask</Name>"
				+ "<Value xsi:type=\"xsd:string\">255.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.6.SourceIP</Name>"
				+ "<Value xsi:type=\"xsd:string\">10.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIP</Name>"
				+ "<Value xsi:type=\"xsd:string\">10.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.4.SourceIPMask</Name>"
				+ "<Value xsi:type=\"xsd:string\">255.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.3.SourceIPMask</Name>"
				+ "<Value xsi:type=\"xsd:string\">255.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.5.SourceIP</Name>"
				+ "<Value xsi:type=\"xsd:string\">10.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.ManagementServer.URL</Name>"
				+ "<Value xsi:type=\"xsd:string\">http://10.243.156.120:57023/Amis/WGCPEMgt</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.ManagementServer.Username</Name>"
				+ "<Value xsi:type=\"xsd:string\">administrator</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.1.SourceIP</Name>"
				+ "<Value xsi:type=\"xsd:string\">10.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.X_000E50_Firewall.Chain.4.Rule.4.SourceIP</Name>"
				+ "<Value xsi:type=\"xsd:string\">10.0.0.0</Value>"
				+ "</ParameterValueStruct>"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.ManagementServer.Password</Name>"
				+ "<Value xsi:type=\"xsd:string\">EpC71249HgUH16KX9821Lu</Value>"
				+ "</ParameterValueStruct>"
				+ "</ParameterList>"
				+ "<ParameterKey>4323432</ParameterKey>"
				+ "</cwmp:SetParameterValues>"
				+ "</SOAP-ENV:Body>"
				+ "</SOAP-ENV:Envelope>";
		
		String message1 = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\" "
				+ "xmlns:soap-enc=\"http://schemas.xmlsoap.org/soap/encoding/\" "
				+ "xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
				+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<SOAP-ENV:Header>"
				+ "<cwmp:ID SOAP-ENV:mustUnderstand=\"1\">test-session</cwmp:ID>"
				+ "</SOAP-ENV:Header>"
				+ "<SOAP-ENV:Body>"
				+ "<cwmp:SetParameterValues>"
				+ "<ParameterList soap-enc:arrayType=\"cwmp:ParameterValueStruct[1]\">"
				+ "<ParameterValueStruct>"
				+ "<Name>InternetGatewayDevice.ManagementServer.URL</Name>"
				+ "<Value xsi:type=\"xsd:string\">http://10.243.156.120:57023/Amis/WGCPEMgt</Value>"
				+ "</ParameterValueStruct>"
				+ "</ParameterList>"
				+ "<ParameterKey>4323432</ParameterKey>"
				+ "</cwmp:SetParameterValues>"
				+ "</SOAP-ENV:Body>"
				+ "</SOAP-ENV:Envelope>";

		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		spvMessage.writeTo(bas);
		String spvMessageString = bas.toString("UTF-8");
		assertEquals(message1, spvMessageString);
	}

}
