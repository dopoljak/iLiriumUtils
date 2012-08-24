package com.iLirium.utils.commons;

import java.security.SecureRandom;

/**
 * @author dopoljak@gmail.com
 */
public class Randoms
{
	private static final int[] max_digits = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };
	
	/**
	 * Generate random number with maximum length 1 - 9
	 */
	public static String getRandom(int lenght)
	{
		SecureRandom secureRandom = new SecureRandom();
		return String.valueOf(Math.abs(secureRandom.nextLong()) % max_digits[lenght]);
	}
}
