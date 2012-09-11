package configuration;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import junit.framework.TestCase;

import org.h2.tools.DeleteDbFiles;

import com.iLirium.utils.configuration.ConfigReader;

public class Test_ConfigReader  extends TestCase
{
	final static String PROP_STRING_LEN = "STRING_LEN";
	final static String PROP_LOG_NAME = "LOG_NAME";
	final static String PROP_USERNAME = "USERNAME";
	final static String PROP_PASSWORD = "PASSWORD";
	final static String PROP_TEST_PROP = "PROP_TEST_PROP";
	
	public void test1() throws ClassNotFoundException, SQLException
	{
		/** example properties **/
		HashMap<String, String> defaultConfig = new HashMap<String, String>();
		defaultConfig.put(PROP_STRING_LEN, "6");
		defaultConfig.put(PROP_LOG_NAME, "app.log");
		defaultConfig.put(PROP_USERNAME, "username00");
		defaultConfig.put(PROP_PASSWORD, "password00");
		defaultConfig.put(PROP_TEST_PROP, "00_00_00");

		defaultConfig.put(ConfigReader.CONFIG_DB_URI, "jdbc:h2:~/test");
		defaultConfig.put(ConfigReader.CONFIG_DB_USERNAME, "username00");
		defaultConfig.put(ConfigReader.CONFIG_DB_PASSWORD, "password00");
		defaultConfig.put(ConfigReader.CONFIG_DB_SELECT, "select * from CONFIG_PROPERTIES");
		defaultConfig.put(ConfigReader.CONFIG_DB_CLASS, "org.h2.Driver");
		
		
		ServletContext servletContext = new ServletContext() 
		{
			
			@Override
			public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) throws IllegalStateException, IllegalArgumentException
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean setInitParameter(String arg0, String arg1)
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setAttribute(String arg0, Object arg1)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeAttribute(String arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void log(String arg0, Throwable arg1)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void log(Exception arg0, String arg1)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void log(String arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public SessionCookieConfig getSessionCookieConfig()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<Servlet> getServlets()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, ? extends ServletRegistration> getServletRegistrations()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ServletRegistration getServletRegistration(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getServletNames()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getServletContextName()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Servlet getServlet(String arg0) throws ServletException
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getServerInfo()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Set<String> getResourcePaths(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public InputStream getResourceAsStream(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public URL getResource(String arg0) throws MalformedURLException
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public RequestDispatcher getRequestDispatcher(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRealPath(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public RequestDispatcher getNamedDispatcher(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getMinorVersion()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getMimeType(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getMajorVersion()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public JspConfigDescriptor getJspConfigDescriptor()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getInitParameterNames()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getInitParameter(String arg0)
			{
				HashMap<String, String> defaultConfig = new HashMap<String, String>();
				defaultConfig.put(PROP_STRING_LEN, "6__");
				defaultConfig.put(PROP_LOG_NAME, "app.log__");
				defaultConfig.put(PROP_PASSWORD, "password00__");
				defaultConfig.put(PROP_TEST_PROP, "11_11_11");
				// TODO Auto-generated method stub
				return defaultConfig.get(arg0);
			}
			
			@Override
			public Map<String, ? extends FilterRegistration> getFilterRegistrations()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public FilterRegistration getFilterRegistration(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Set<SessionTrackingMode> getEffectiveSessionTrackingModes()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getEffectiveMinorVersion()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getEffectiveMajorVersion()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Set<SessionTrackingMode> getDefaultSessionTrackingModes()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContextPath()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ServletContext getContext(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ClassLoader getClassLoader()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getAttributeNames()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void declareRoles(String... arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public <T extends Servlet> T createServlet(Class<T> arg0) throws ServletException
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T extends EventListener> T createListener(Class<T> arg0) throws ServletException
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T extends Filter> T createFilter(Class<T> arg0) throws ServletException
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Class<? extends Servlet> arg1)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Servlet arg1)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, String arg1)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T extends EventListener> void addListener(T arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addListener(String arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addListener(Class<? extends EventListener> arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Dynamic addFilter(String arg0, Class<? extends Filter> arg1)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Dynamic addFilter(String arg0, Filter arg1)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Dynamic addFilter(String arg0, String arg1)
			{
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		createTestDB(defaultConfig);
		
		ConfigReader config = new ConfigReader(defaultConfig, servletContext);
		
		System.out.println("--------------------------------------------------------------");
		System.out.println(config.toString());
		System.out.println("--------------------------------------------------------------");
		
		
		System.out.println(" ### " + config.getString(PROP_TEST_PROP));
		System.out.println(" ### " + config.getString(PROP_LOG_NAME));
	}
	
	private void createTestDB(HashMap<String, String> defaultConfig) throws ClassNotFoundException, SQLException
	{
		String db_url = defaultConfig.get(ConfigReader.CONFIG_DB_URI);
		String db_user = defaultConfig.get(ConfigReader.CONFIG_DB_USERNAME);
		String db_pass = defaultConfig.get(ConfigReader.CONFIG_DB_PASSWORD);
		String db_class = defaultConfig.get(ConfigReader.CONFIG_DB_CLASS);
		@SuppressWarnings("unused")
		String db_select = defaultConfig.get(ConfigReader.CONFIG_DB_SELECT);
			
		DeleteDbFiles.execute("~", "test", true);
		
		Class.forName(db_class);
		Connection connection = DriverManager.getConnection(db_url, db_user, db_pass);
		Statement statement = connection.createStatement();
		statement.execute("create table CONFIG_PROPERTIES (key varchar(255) primary key, value varchar(255))");
		statement.execute("insert into CONFIG_PROPERTIES values('Key00', 'Value00')");
		statement.execute("insert into CONFIG_PROPERTIES values('USERNAME', 'USERNAME_aa_bb')");
		statement.execute("insert into CONFIG_PROPERTIES values('PROP_TEST_PROP', '33_33_33_DB')");
		
		/*
		ResultSet rs = stat.executeQuery(db_select);
		while (rs.next()) {
			System.out.println(rs.getString("key") + " : " + rs.getString("value"));
		}*/
		
		statement.close();
		connection.close();
	}
}
