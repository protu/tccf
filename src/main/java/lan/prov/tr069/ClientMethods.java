package lan.prov.tr069;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lan.prov.client.GenericDevice;
import lan.prov.tr069.CWMPMessage;

public class ClientMethods extends CWMPMessage {
	
	public SOAPMessage clientPeriodicInform(GenericDevice device) throws Exception {
		
		SOAPMessage informMessage = genericMessage();
		SOAPBody soapBody = informMessage.getSOAPBody();
		SOAPElement inform = soapBody.addChildElement("Inform", "cwmp");
		
		SOAPElement deviceID = inform.addChildElement("DeviceId");
		deviceID.addChildElement("Manufacturer").setValue(device.getManufacturer());
		deviceID.addChildElement("OUI").setValue(device.getOUI());
		deviceID.addChildElement("ProductClass").setValue(device.getProductClass());
		deviceID.addChildElement("SerialNumber").setValue(device.getSerialNumber());
		
		SOAPElement event = inform.addChildElement("Event");
//		event.addAttribute(getQnXsiType(), "soap-enc:Array");
		event.addAttribute(getQnSoapEncArrayType(), "cwmp:EventStruct[1]");
		
		SOAPElement eventStruct = event.addChildElement("EventStruct");
		eventStruct.addChildElement("EventCode").setValue("2 PERIODIC");
		eventStruct.addChildElement("CommandKey");
		inform.addChildElement("MaxEnvelopes").setValue("1");
		inform.addChildElement("CurrentTime").setValue(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		inform.addChildElement("RetryCount").setValue("0");
		
		SOAPElement parameterList = inform.addChildElement("ParameterList");
//		parameterList.addAttribute(getQnXsiType(), "soap-enc:Array");
		parameterList.addAttribute(getQnSoapEncArrayType(), "cwmp:ParameterValueStruct[8]");
		parameterList.addChildElement(parameterValueStruct("InternetGatewayDevice.DeviceSummary", device.getDeviceSummary(), "xsd:string"));
		parameterList.addChildElement(parameterValueStruct("InternetGatewayDevice.DeviceInfo.SpecVersion", device.getSpecVersion(), "xsd:string"));
		parameterList.addChildElement(parameterValueStruct("InternetGatewayDevice.DeviceInfo.HardwareVersion", device.getHardwareVersion(), "xsd:string"));
		parameterList.addChildElement(parameterValueStruct("InternetGatewayDevice.DeviceInfo.SoftwareVersion", device.getSoftwareVersion(), "xsd:string"));
		parameterList.addChildElement(parameterValueStruct("InternetGatewayDevice.DeviceInfo.ProvisioningCode", device.getProvisioningCode(), "xsd:string"));
		parameterList.addChildElement(parameterValueStruct("InternetGatewayDevice.DeviceInfo.ConnectionRequestURL", device.getConnectionRequestURL(), "xsd:string"));
		parameterList.addChildElement(parameterValueStruct("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANIPConnection.1.ExternalIPAddress", device.getExternalIPAddress(), "xsd:string"));
		parameterList.addChildElement(parameterValueStruct("InternetGatewayDevice.ManagementServer.ParameterKey", "", "xsd:string"));
		
		return informMessage;
	}

}
