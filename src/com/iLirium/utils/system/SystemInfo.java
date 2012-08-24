package com.iLirium.utils.system;

import java.io.File;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import com.iLirium.utils.commons.Strings;

/**
 * @author dopoljak@gmail.com
 */
public class SystemInfo
{
	private static final long	MB	= 1024L * 1024L;

	/**
	 * Get memory info
	 */
	public static String getMemoryData()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("----------------------------------").append(Strings.NEWLINE);
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		sb.append("Main & Deamon Thread Count in use          : ").append(threadMXBean.getThreadCount()).append(Strings.NEWLINE);
		sb.append("Peak Thread Count in use                   : ").append(threadMXBean.getPeakThreadCount()).append(Strings.NEWLINE);
		sb.append("Available processors (cores)               : ").append(Runtime.getRuntime().availableProcessors()).append(Strings.NEWLINE);
		sb.append("Free memory available for JVM  (MB)        : ").append(Runtime.getRuntime().freeMemory() / MB).append(Strings.NEWLINE);
		sb.append("Maximum memory JVM will attempt to use(MB) : ").append(Runtime.getRuntime().maxMemory() / MB).append(Strings.NEWLINE);
		sb.append("Total memory in use by JVM            (MB) : ").append(Runtime.getRuntime().totalMemory() / MB).append(Strings.NEWLINE);

		sb.append("----------------------------------").append(Strings.NEWLINE);
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		sb.append("Heap Memory Usage     : ").append(memoryMXBean.getHeapMemoryUsage()).append(Strings.NEWLINE);
		sb.append("Non-Heap Memory Usage : ").append(memoryMXBean.getNonHeapMemoryUsage()).append(Strings.NEWLINE);

		sb.append("----------------------------------").append(Strings.NEWLINE);
		List<GarbageCollectorMXBean> garbaceCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean gcBean : garbaceCollectorMXBeans) {
			sb.append("Name              : ").append(gcBean.getName()).append(Strings.NEWLINE);
			sb.append("Collection count  : ").append(gcBean.getCollectionCount()).append(Strings.NEWLINE);
			sb.append("Collection time   : ").append(gcBean.getCollectionTime()).append(Strings.NEWLINE);
			sb.append("Memory Pool Names : ").append(Strings.NEWLINE);
			String[] memoryPoolNames = gcBean.getMemoryPoolNames();
			for (int i = 0; i < memoryPoolNames.length; i++) {
				sb.append("\t").append(memoryPoolNames[i]).append(Strings.NEWLINE);
			}
		}

		sb.append("----------------------------------").append(Strings.NEWLINE);
		sb.append("Memory Pools Info").append(Strings.NEWLINE);
		List<MemoryPoolMXBean> mempoolsmbeans = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean mempoolmbean : mempoolsmbeans) {
			sb.append("Name                 : ").append(mempoolmbean.getName()).append(Strings.NEWLINE);
			sb.append("Usage                : ").append(mempoolmbean.getUsage()).append(Strings.NEWLINE);
			sb.append("Collection Usage     : ").append(mempoolmbean.getCollectionUsage()).append(Strings.NEWLINE);
			sb.append("Peak Usage           : ").append(mempoolmbean.getPeakUsage()).append(Strings.NEWLINE);
			sb.append("Type                 : ").append(mempoolmbean.getType()).append(Strings.NEWLINE);
			sb.append("Memory Manager Names : ").append(Strings.NEWLINE);
			String[] memManagerNames = mempoolmbean.getMemoryManagerNames();
			for (int i = 0; i < memManagerNames.length; i++) {
				sb.append("\t").append(memManagerNames[i]).append(Strings.NEWLINE);
			}
		}

		return sb.toString();
	}

	/**
	 * Get useful system properties
	 */
	public static String getSystemProperties()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("OS Name            : ").append(getSystemProperty("os.name")).append(Strings.NEWLINE);
		sb.append("OS Version         : ").append(getSystemProperty("os.version")).append(Strings.NEWLINE);
		sb.append("OS Arch            : ").append(getSystemProperty("os.arch")).append(Strings.NEWLINE);
		sb.append("OS Type            : ").append(getOSType()).append(Strings.NEWLINE);
		
		sb.append("Java Version       : ").append(getSystemProperty("java.version")).append(Strings.NEWLINE);
		sb.append("Java Vendor        : ").append(getSystemProperty("java.vendor")).append(Strings.NEWLINE);
		sb.append("Java Home          : ").append(getSystemProperty("java.home")).append(Strings.NEWLINE);
		sb.append("Java Class Path    : ").append(getSystemProperty("java.class.path")).append(Strings.NEWLINE);
		sb.append("Java TMP Directory : ").append(getSystemProperty("java.io.tmpdir")).append(Strings.NEWLINE);
		sb.append("Java Runtime ver   : ").append(getSystemProperty("java.runtime.version")).append(Strings.NEWLINE);
		sb.append("Java VM Name       : ").append(getSystemProperty("java.vm.name")).append(Strings.NEWLINE);
		
		sb.append("HostName           : ").append(getHostname()).append(Strings.NEWLINE);
		sb.append("IP Addresses       : ").append(getLocalIPAddresses()).append(Strings.NEWLINE);
		
		sb.append("User directory     : ").append(getDirectory("user.dir")).append(Strings.NEWLINE);
		sb.append("User home          : ").append(getDirectory("user.home")).append(Strings.NEWLINE);
		sb.append("User name          : ").append(getSystemProperty("user.name")).append(Strings.NEWLINE);
		sb.append("File separator     : ").append(getSystemProperty("file.separator")).append(Strings.NEWLINE);

		return sb.toString();
	}

	/**
	 * Get system property, returns null if call has not inaff privileges
	 */
	private static String getSystemProperty(String property)
	{
		try {
			return System.getProperty(property);
		}
		catch (SecurityException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Get hostname
	 */
	public static String getHostname()
	{
		try {
			return InetAddress.getLocalHost().getHostName();
		}
		catch (Exception e) {
		}
		return null;
	}

	/**
	 * Get current operating system
	 */
	public static String getOSType()
	{
		String os = getSystemProperty("os.name").toLowerCase();

		if (os.indexOf("win") >= 0)
			return "WINDOWS";

		if (os.indexOf("mac") >= 0)
			return "MAC OS";

		if (os.indexOf("nux") >= 0)
			return "LINUX";

		if (os.indexOf("nix") >= 0)
			return "UNIX";

		if (os.indexOf("aix") >= 0)
			return "AIX";

		if (os.indexOf("sunos") >= 0)
			return "SOLARIS";

		return "UNKNOWN OS";
	}

	/**
	 * Get all local IP addresses
	 */
	public static String getLocalIPAddresses()
	{
		StringBuilder sb = new StringBuilder();

		try {
			InetAddress localhost = InetAddress.getLocalHost();
			sb.append("Main IP : ").append(localhost.getHostAddress());
			InetAddress[] otherAdresses = InetAddress.getAllByName(localhost.getCanonicalHostName());
			if (otherAdresses != null && otherAdresses.length > 1) {
				sb.append(" Other IPs : ");
				for (InetAddress inetAddress : otherAdresses) {
					sb.append(inetAddress.getHostAddress()).append(", ");
				}
			}
		}
		catch (UnknownHostException e) {
		}

		return sb.toString();
	}

	/**
	 * Get Directory and append file separator
	 */
	public static String getDirectory(String directory)
	{
		if (directory == null || directory.length() <= 0)
			return null;

		StringBuilder sbDirectory = new StringBuilder();
		File file = new File(getSystemProperty(directory));
		sbDirectory.append(file.getAbsolutePath());
		if (!sbDirectory.toString().endsWith(File.separator)) {
			sbDirectory.append(File.separator);
		}
		return sbDirectory.toString();
	}
}
