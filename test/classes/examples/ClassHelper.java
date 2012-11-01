package classes.examples;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copied from:
 * http://oohhyeah.blogspot.com/2012/01/use-java-reflection-to-find-classes.html
 * https://svn.apache.org/repos/asf/incubator/wink/contrib/ibm-jaxrs/tests/unittests/com/ibm/ws/jaxrs/web/samples/ServerHost.java
 * 
 */
public class ClassHelper
{
	private static final Logger	log	= LoggerFactory.getLogger(ClassHelper.class);

	public static List<Class<?>> findClassesImpmenenting(final Class<?> interfaceClass, final Package fromPackage)
	{

		if (interfaceClass == null) {
			log.info("Unknown subclass.");
			return null;
		}

		if (fromPackage == null) {
			log.info("Unknown package.");
			return null;
		}

		final List<Class<?>> rVal = new ArrayList<Class<?>>();
		try {
			final Class<?>[] targets = getClasses(fromPackage.getName());
			if (targets != null) {
				for (Class<?> aTarget : targets) {
					if (aTarget == null) {
						continue;
					}
					else if (aTarget.equals(interfaceClass)) {
						log.info("Found the interface definition.");
						continue;
					}
					else if (!interfaceClass.isAssignableFrom(aTarget)) {
						log.info("Class '" + aTarget.getName() + "' is not a " + interfaceClass.getName());
						continue;
					}
					else {
						rVal.add(aTarget);
					}
				}
			}
		}
		catch (ClassNotFoundException e) {
			log.info("Error reading package name.", e);
		}
		catch (IOException e) {
			log.info("Error reading classes in package.", e);
		}

		return rVal;
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			}
			else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getClasses2(String packageName) throws ClassNotFoundException, IOException
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		if (packageName == null)
			packageName = "";
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		ArrayList<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> findClasses2(File directory, String packageName) throws ClassNotFoundException
	{
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				if (packageName.equals(""))
					classes.addAll(findClasses(file, file.getName()));
				else
					classes.addAll(findClasses(file, packageName + "." + file.getName()));
			}
			else if (file.getName().endsWith(".class")) {
				try {
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				}
				catch (NoClassDefFoundError e) {

				}
			}
		}
		return classes;
	}

}
