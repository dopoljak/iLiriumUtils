package com.iLirium.utils.commons;

/**
 * 
 * @author dopoljak@gmail.com
 * 
 */
public class Assert
{
	public static <T> T notNull(T object, String message) throws IllegalArgumentException
	{
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
		return object;
	}

	public static <T> T notNull(T object) throws IllegalArgumentException
	{
		return notNull(object, null);
	}

	public static <T> T[] notNulls(T... objects) throws IllegalArgumentException
	{
		notNull(objects);
		for (int i = 0; i < objects.length; i++) {
			notNull(objects[i]);
		}
		return objects;
	}

	public static <T> T[] notNulls(T objects[], String message) throws IllegalArgumentException
	{
		notNull(objects, message);
		for (int i = 0; i < objects.length; i++) {
			notNull(objects[i], message);
		}
		return objects;
	}

	public static void isTrue(boolean variable, String message) throws IllegalArgumentException
	{
		if (variable) {
			throw new IllegalArgumentException(message);
		}
	}

	public static String isEmpty(String string, String message) throws IllegalArgumentException
	{
		if (Strings.isEmpty(string)) {
			throw new IllegalArgumentException(message);
		}
		return string;
	}

	public static String isBlank(String string, String message) throws IllegalArgumentException
	{
		if (Strings.isBlank(string)) {
			throw new IllegalArgumentException(message);
		}
		return string;
	}
}
