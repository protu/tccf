package lan.prov.client;

public class TccfCliClient {
	
	public static void main(String[] args) {
		SoapClient client = new SoapClient();
		client.SendMessage();
	}

}
