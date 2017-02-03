package lan.prov.parse;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DeviceHandler extends DefaultHandler {

	private Boolean bSessionID = false;
	private Boolean bManufacturer = false;
	private Boolean bOUI = false;
	private Boolean bProductClass = false;
	private Boolean bSerialNumber = false;
	private Boolean bCommandKey = false;
	private Boolean bEventCode = false;

	private int eventsNo;
	private List<Map<String, String>> eventsList = new ArrayList<Map<String, String>>();
	private Map<String, String> eventMap;
	
	private Device device = new Device();
	
	public List<Map<String, String>> getEventsList() {
		return eventsList;
	}
	
	public Device getDevice() {
		return device;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.contentEquals("cwmp:ID")) {
			bSessionID = true;
		}
		if (qName.contentEquals("Manufacturer")) {
			bManufacturer = true;
		}
		if (qName.contentEquals("OUI")) {
			bOUI = true;
		}
		if (qName.contentEquals("ProductClass")) {
			bProductClass = true;
		}
		if (qName.contentEquals("SerialNumber")) {
			bSerialNumber = true;
		}
		if (qName.contentEquals("Event")) {
			String value = attributes.getValue("soap:arrayType");
			eventsNo = Integer.parseInt(value.substring(value.length() - 3, value.length() - 1));
		}
		if (qName.contentEquals("EventStruct")) {
			eventMap = new HashMap<String, String>();
		}
		if (qName.contentEquals("EventCode") && eventsNo > 0) {
			bEventCode = true;
		}
		if (qName.contentEquals("CommandKey") && eventsNo > 0) {
			bCommandKey = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.contentEquals("EventStruct")) {
			eventsList.add(eventMap);
			eventsNo--;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (bSessionID) {
			device.setSessionID(new String(ch, start, length));
			bSessionID = false;
		}
		if (bManufacturer) {
			device.setManufacturer(new String(ch, start, length));
			bManufacturer = false;
		}
		if (bProductClass) {
			device.setProductClass(new String(ch, start, length));
			bProductClass = false;
		}
		if (bOUI) {
			device.setOUI(new String(ch, start, length));
			bOUI = false;
		}
		if (bSerialNumber) {
			device.setSerialNumber(new String(ch, start, length));
			bSerialNumber = false;
		}
		if (bEventCode) {
			eventMap.put("EventCode", new String(ch, start, length));
			bEventCode = false;
		}
		if (bCommandKey) {
			eventMap.put("CommandKey", new String(ch, start, length));
			bCommandKey = false;
		}
	}

}
