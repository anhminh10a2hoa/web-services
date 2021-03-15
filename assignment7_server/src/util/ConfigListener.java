package util;
import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import restful.data.EmployeeDao;
public class ConfigListener implements ServletContextListener {
    
        private String destinationDir;
        private String dbFileName;
        private String separator=File.separator;
    
    @Override
    public void contextInitialized(ServletContextEvent servletContextListener) {
      
        ServletContext servletContext = servletContextListener.getServletContext();
            
        String db_url=servletContext.getInitParameter("db_url");
        String db_username=servletContext.getInitParameter("db_username");
        String db_password=servletContext.getInitParameter("db_password");
        EmployeeDao.setDBFileName(db_url, db_username, db_password);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}