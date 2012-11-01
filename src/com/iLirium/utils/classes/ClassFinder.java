package com.iLirium.utils.classes;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://svn.apache.org/repos/asf/incubator/wink/contrib/ibm-jaxrs/tests/
 * unittests/com/ibm/ws/jaxrs/web/samples/ServerHost.java
 * 
 */

public class ClassFinder
{
	private static final Logger	log	= LoggerFactory.getLogger(ClassFinder.class);
	
	/**
	 * Get all classes with given Interface
	 * @param packageName
	 * @param iface
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Class[] getInterfaceClasses(String packageName, Class iface) throws ClassNotFoundException, IOException
	{
		ArrayList<Class> classes = new ArrayList<Class>();
		Class[] ca = getClasses(packageName);
		if (ca != null) {
			for (Class<?> clazz : ca) {
				if (clazz == null) {
					continue;
				}
				else if (clazz.equals(iface)) {
					log.debug("Found the interface definition.");
					continue;
				}
				else if (!iface.isAssignableFrom(clazz)) {
					log.debug("Class '{}' is not a '{}'", clazz.getName(), iface.getName());
					continue;
				}
				else {
					classes.add(clazz);
				}
			}
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Get all classes with defined annotation
	 * @param packageName
	 * @param iface
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Class[] getAnnotationClasses(String packageName, Class iface) throws ClassNotFoundException, IOException
	{
		Class[] ca = getClasses(packageName);
		ArrayList<Class> result = new ArrayList<Class>();
		for (int i = 0; i < ca.length; i++) {
			if (ca[i].getAnnotation(iface) != null) {
				log.debug("Founded class with given annotation {}", ca[i]);
				result.add(ca[i]);
			}
		}

		Class[] ret = new Class[result.size()];
		return result.toArray(ret);
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
	private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException
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
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException
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

