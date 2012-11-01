package com.iLirium.utils.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 * @author dopoljak@gmail.com
 * 
 * Simple configuration reader by priorities
 *  - PRIORITY 0 - load all default parameters (only default KEY values are used)
 *  - PRIORITY 1 - load all web.xml context-param
 *  - PRIORITY 2 - load all JNDI parameters
 *  - PRIORITY 3 - load all Database parameters
 */
public final class ConfigReader
{
	static class log
	{
		static void info(Object ...args)  {};
		static void debug(Object ...args) {};
		static void trace(Object ...args) {};
	}

	public static final String CONFIG_JNDI_NAME		= "CONFIG_JNDI_NAME";
	public static final String CONFIG_DB_URI 		= "CONFIG_DB_URI";
	public static final String CONFIG_DB_USERNAME 	= "CONFIG_DB_USERNAME";
	public static final String CONFIG_DB_PASSWORD 	= "CONFIG_DB_PASSWORD";
	public static final String CONFIG_DB_CLASS 		= "CONFIG_DB_CLASS";
	public static final String CONFIG_DB_SELECT 	= "CONFIG_DB_SELECT";

	private final HashMap<String, String>	loadedConfig		= new HashMap<String, String>();
	private final HashMap<String, String>	loadedConfigFrom	= new HashMap<String, String>();
	
	private int longestKeyName = 0;

	public ConfigReader(Class<?> clazz, Object servletContext) throws IllegalArgumentException, IllegalAccessException
	{
		// get all properties from received class
		log.debug(":::::::: LOADING 0 PRIORITY DEFAULT PROPERTIES :::::::::");
		for (Field f : clazz.getDeclaredFields()) 
		{
			if (f.getType() == String.class || f.getType() == Boolean.class || f.getType() == Integer.class)
			{
				f.setAccessible(true);
				String name = f.getName();
				String value = f.get(null) == null ? null : String.valueOf(f.get(null));
				loadedConfig.put(name, value);
				loadedConfigFrom.put(name, "PRIORITY 0 DEFAULT ");
				log.trace("DEFAULT : Key = " + name + ", Value = " + value);
				if(name != null && name.length() > longestKeyName)
					longestKeyName = name.length();
			}
		}
		
		/** get Servlet Context parameters **/
		loadServletContextParameters(servletContext);

		/** get JNDI parameters **/		
		try {
			loadJNDIParameters();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		/** get DATABASE highest priority database parameters **/
		try {
			loadDatabaseParameters();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		

		for (Field f : clazz.getDeclaredFields()) 
		{
			f.setAccessible(true);
			String value = getString(f.getName());

			if (f.getType() == String.class) {
				f.set(null, value);
			}
			else if (f.getType() == Boolean.class) {
				f.set(null, Boolean.valueOf(value));
			}
			else if (f.getType() == Integer.class) {
				f.set(null, Integer.valueOf(value));
			}
		}
	}
	
	/**
	 * Load parameters from all configuration sources by default priority
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws NamingException 
	 */
	public ConfigReader(HashMap<String, String> defaultConfig, Object servletContext)
	{
		log.debug(":::::::: LOADING 0 PRIORITY DEFAULT PROPERTIES :::::::::");
		for (Map.Entry<String, String> entry : defaultConfig.entrySet()) 
		{
			String name = entry.getKey();
			loadedConfig.put(name, entry.getValue());
			loadedConfigFrom.put(name, "PRIORITY 0 DEFAULT ");
			log.trace("DEFAULT : Key = " + name + ", Value = " + entry.getValue());
			
			if(name != null && name.length() > longestKeyName)
				longestKeyName = name.length();
		}

		/** get Servlet Context parameters **/
		loadServletContextParameters(servletContext);

		/** get JNDI parameters **/		
		try {
			loadJNDIParameters();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		/** get DATABASE highest priority database parameters **/
		try {
			loadDatabaseParameters();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Load Servlet parameters
	 * @param servletContext
	 */
	private void loadServletContextParameters(Object servletContext)
	{
		if (servletContext != null) 
		{
			log.debug(":::::::: LOADING 1 PRIORITY WEB.XML PROPERTIES :::::::::");
			for (String key : loadedConfig.keySet())
			{
				try 
				{
					Method m = servletContext.getClass().getMethod("getInitParameter", new Class[]{ String.class });
					m.setAccessible(true);
					String value = (String) m.invoke(servletContext, new Object[] { key } );
					if(value != null) 
					{
						loadedConfig.put(key, value);
						loadedConfigFrom.put(key, "PRIORITY 1 WEB.XML ");
						log.trace("WEB.XML : Key = " + key + ", Value = " + value);
					}
				}
				catch (Exception e) {
					log.trace("Error loading WEX.XML properties ", e);
				}
			}
		}
	}
	
	/**
	 * Load parameters from JNDI
	 * @throws NamingException
	 */
	private void loadJNDIParameters() throws NamingException
	{
		String jndi = loadedConfig.get(CONFIG_JNDI_NAME);
		if(jndi != null) 
		{
			log.debug(":::::::: LOADING 2 PRIORITY JNDI RESOURCE PROPERTIES :::::::::");
			
			Context env = (Context)new InitialContext().lookup("java:comp/" + jndi);
			
			for (String key : loadedConfig.keySet())
			{
				String value = (String)env.lookup(key);
				if(value != null) 
				{
					loadedConfig.put(key, value);
					loadedConfigFrom.put(key, "PRIORITY 2    JNDI ");
					log.trace("JNDI    : Key = " + key + ", Value = " + value);
				}
			}			
		}
	}
	
	/**
	 * Load parameters from Database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void loadDatabaseParameters() throws ClassNotFoundException, SQLException
	{
		String db_url = loadedConfig.get(CONFIG_DB_URI);
		String db_user = loadedConfig.get(CONFIG_DB_USERNAME);
		String db_pass = loadedConfig.get(CONFIG_DB_PASSWORD);
		String db_class = loadedConfig.get(CONFIG_DB_CLASS);
		String db_select = loadedConfig.get(CONFIG_DB_SELECT);

		if(db_url != null && !db_url.isEmpty())
		{
			log.debug(":::::::: LOADING 3 PRIORITY DATABASE PROPERTIES :::::::::");

			Class.forName(db_class);
			Connection connection = DriverManager.getConnection(db_url, db_user, db_pass);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(db_select);

			while (resultSet.next()) 
			{
				String key = resultSet.getString(1);
				String value = resultSet.getString(2);
				if(loadedConfig.get(key) != null && value != null)
				{
					loadedConfig.put(key, value);
					loadedConfigFrom.put(key, "PRIORITY 3 DATABASE");
					log.trace("DATABASE : Key = " + key + ", Value = " + value);
				}
			}

			resultSet.close();
			statement.close();
			connection.close();
		}
	}
	
	
	/**
	 * Print loaded configuration properties
	 */
	@Override
	public String toString()
	{
		String pre_append = "%-"+ longestKeyName +"s";
		StringBuilder sb = new StringBuilder(System.getProperty("line.separator"));
		for (Map.Entry<String, String> entry : loadedConfig.entrySet())
		{
			sb.append(loadedConfigFrom.get(entry.getKey()));
			sb.append(" - ").append(String.format(pre_append, entry.getKey()));
			sb.append(" = ").append(entry.getValue());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	/**
	 * Get parameter as Integer value
	 */
	public Integer getInteger(String key)
	{
		return Integer.valueOf(loadedConfig.get(key));
	}
	
	/**
	 * Get parameter as String value
	 */
	public String getString(String key)
	{
		return (loadedConfig.get(key));
	}
	
	/**
	 * Get parameter as Boolean value
	 */
	public Boolean getBool(String key)
	{
		String value = loadedConfig.get(key);
		
		if(value == null)
			return null;
		
		final String trueString[] = new String[] { "1", "true", "ok", "yes" };
		for (String string : trueString) {
			if(value.equalsIgnoreCase(string))
				return true;
		}
		
		return false;
	}
	
}
