package ws.employee.service;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.OperationContext;
import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.ServiceLifeCycle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringInit implements ServiceLifeCycle  {

	private static Log logger = LogFactory.getLog(SpringInit.class);

	public void init(ServiceContext serviceContext) {
	}

	public void setOperationContext(OperationContext arg0) {
	}

	public void destroy(ServiceContext arg0) {
	}

	/*
	 *
	 * The following method will be called during the deployment time of the
	 *
	 * service regardless of the service scope.
	 *
	 */
	public void startUp(ConfigurationContext ignore, AxisService service) {

		ClassLoader classLoader = service.getClassLoader();

		ClassPathXmlApplicationContext appCtx = new

		ClassPathXmlApplicationContext(new String[] { "conf/applicationContext.xml" }, false);

		appCtx.setClassLoader(classLoader);

		appCtx.refresh();

		if (logger.isDebugEnabled()) {

			logger.debug("\n\nstartUp() set spring classloader via axisService.getClassLoader() ... ");

		}

	}
	/*
	 *
	 * This following method will be called during the deployment time of the
	 *
	 * service regardless of the service scope.
	 *
	 */
	public void shutDown(ConfigurationContext ignore, AxisService service) {
	}
}