package lan.prov.parse;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RequestParser {

	public Device parse(String request) {

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DeviceHandler userhandler = new DeviceHandler();
			saxParser.parse(request, userhandler);
			return userhandler.getDevice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
