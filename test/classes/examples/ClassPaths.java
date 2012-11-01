package classes.examples;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * http://stackoverflow.com/questions/1062804/how-to-find-classes-when-running-an-executable-jar
 */
public class ClassPaths
{
	public static String[] getClasspathFromProperty()
	{
		return System.getProperty("java.class.path").split(File.pathSeparator);
	}
	
	public static String[] getClasspathFromCurrentThread()
	{
		URLClassLoader classLoader = (URLClassLoader) (Thread.currentThread().getContextClassLoader());
		URL classpath[] = classLoader.getURLs();
		String[] classpathLocations = new String[classpath.length];
		for (int i = 0; i < classpath.length; i++) {
			classpathLocations[i] =  classpath[i].toExternalForm();	
		}
		return classpathLocations;
	}

	public String[] getClasspathFromClassloader()
	{
		URLClassLoader classLoader = (URLClassLoader) (getClass().getClassLoader());
		URL[] classpath = classLoader.getURLs();
		if (classpath.length == 1 && classpath[0].toExternalForm().indexOf("surefirebooter") >= 0) {
			// todo: read classpath from manifest in surefireXXXX.jar
			System.err.println("NO PROPER CLASSLOADER HERE!");
			System.err.println("Run maven with -Dsurefire.useSystemClassLoader=false " + "-Dsurefire.useManifestOnlyJar=false to enable proper classloaders");
			return null;
		}
		String[] classpathLocations = new String[classpath.length];
		for (int i = 0; i < classpath.length; i++) {
			// you must repair the path strings: "\.\" => "/" etc.
			classpathLocations[i] =  classpath[i].toExternalForm(); //cleanClasspathUrl(classpath[i].toExternalForm());
		}
		return classpathLocations;
	}
}
