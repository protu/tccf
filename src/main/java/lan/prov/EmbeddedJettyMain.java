package lan.prov;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import lan.prov.srvlets.DeviceListenServlet;

public class EmbeddedJettyMain {

	public static void main(String[] args) throws Exception {

		Server server = new Server(10301);
		ServletContextHandler handler = new ServletContextHandler(server, "/");
		handler.addServlet(DeviceListenServlet.class, "/acs/croatia/ULL");
		server.start();
		server.join();
	}

}
