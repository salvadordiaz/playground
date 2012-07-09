package fr.salvadordiaz.gwt.schedule.server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManifestServlet extends HttpServlet {
	private static final long serialVersionUID = -5174173274995339549L;

	private static final Logger LOGGER = Logger.getLogger(ManifestServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/cache-manifest");
		resp.setCharacterEncoding(Charset.defaultCharset().name());
		LOGGER.log(Level.FINE, "Sending reponse encoded as " + Charset.defaultCharset().name());

		String moduleDir = "schedule";
		File file = new File(moduleDir);
		StringBuffer buffer = new StringBuffer();
		appendFileNames(buffer, file, moduleDir + "/");

		resp.getWriter().println("CACHE MANIFEST");
		resp.getWriter().println("index.html");
		String manifestContents = buffer.toString();
		resp.getWriter().println(manifestContents);
		resp.flushBuffer();
		resp.getWriter().close();
		LOGGER.log(Level.WARNING, "Sent manifest contents : " + manifestContents);
	}

	private static void appendFileNames(StringBuffer buffer, File dir, String prefix) {
		for (File file : dir.listFiles()) {
			if (canRead(file))
				if (file.isDirectory()) {
					appendFileNames(buffer, file, prefix + file.getName() + "/");
				} else {
					buffer.append("\n");
					buffer.append(prefix + file.getName());
				}
		}
	}

	private static boolean canRead(File file) {
		try {
			return file.canRead();
		} catch (Exception e) {
			LOGGER.log(Level.FINE, "Unable to read file " + file.getName());
			return false;
		}
	}
}
