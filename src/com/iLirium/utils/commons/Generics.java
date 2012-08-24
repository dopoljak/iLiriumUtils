package com.iLirium.utils.commons;

/**
 * 
 * @author dopoljak@gmail.com
 *
 */
public class Generics
{
	/**
	 * Check is object null
	 */
	public static <T> T notNull(T object)
	{
		if (object == null) {
			throw new NullPointerException();
		}
		return object;
	}
}
