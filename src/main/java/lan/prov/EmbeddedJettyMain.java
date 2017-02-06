package lan.prov;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DebugHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.RolloverFileOutputStream;

import lan.prov.srvlets.DeviceListenServlet;

public class EmbeddedJettyMain {

	public static void main(String[] args) throws Exception {

		Server server = new Server(10301);
		ServletContextHandler handler = new ServletContextHandler(server, "/");
		handler.addServlet(DeviceListenServlet.class, "/acs/croatia/ULL");

		RolloverFileOutputStream outputStream = new RolloverFileOutputStream("log/yyyy_mm_dd.request.log", true,
				10);

		DebugHandler debugHandler = new DebugHandler();
		debugHandler.setOutputStream(outputStream);
		debugHandler.setHandler(server.getHandler());

		server.setHandler(debugHandler);

		server.start();
		server.join();
	}

}
