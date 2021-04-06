package util;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import helper.FileHandler;

public class ConfigListener implements ServletContextListener {
	private String destinationDir;
	@Override
	public void contextInitialized(ServletContextEvent servletContextListener) {

		ServletContext servletContext = servletContextListener.getServletContext();
		destinationDir = servletContext.getRealPath(servletContext.getInitParameter("destination_dir")) + File.separator;

		File file = new File(destinationDir);
		if (!file.exists())
			file.mkdirs();

		FileHandler.setDestinationDir(destinationDir);
		FileHandler.setDBFileName("db.txt");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}