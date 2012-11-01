package configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iLirium.utils.configuration.ConfigReader;

public final class Config
{
	public static String	NAME_TMP_DIR		= "tmpDir";
	public static String	NAME_CONF_DIR		= "confDir";
	public static String	NAME_LANGUAGE_DIR	= "language";
	public static String	NAME_ENDPOINT		= "endpoint";
	public static String	NAME_JKS			= "javaKeyStore";
	public static String	NAME_JKS_PASS		= "javaKeyStorePass";

	public synchronized static void initialize(Object servletContext) throws IllegalArgumentException, IllegalAccessException
	{
		if (config == null) {
			log.info("#################################### CONFIG PARAMETERS ########################################");
			config = new ConfigReader(Config.class, servletContext);
			log.info(config.toString());
			log.info("###############################################################################################");
		}
	}

	private static ConfigReader	config;
	private static final Logger	log	= LoggerFactory.getLogger(Config.class);
}
