package lan.prov;

import java.io.PrintStream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.RolloverFileOutputStream;

import lan.prov.srvlets.DeviceListenServlet;

public class EmbeddedJettyMain {

	public static void main(String[] args) throws Exception {

		RolloverFileOutputStream logFileRolloverStream = new RolloverFileOutputStream("log/yyyy_mm_dd_tccf.log", true);
		PrintStream logFileStream = new PrintStream(logFileRolloverStream);
		System.setErr(logFileStream);
		System.setOut(logFileStream);
		
		Server server = new Server(10301);
		ServletContextHandler handler = new ServletContextHandler(server, "/");
		SessionHandler sessionHandler = new SessionHandler();
		handler.setSessionHandler(sessionHandler);
		handler.addServlet(DeviceListenServlet.class, "/acs/croatia/ULL");

		server.start();
		server.dumpStdErr();
		server.join();
	}

}
