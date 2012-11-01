package com.iLirium.utils.commons;

/**
 * @author dopoljak@gmail.com
 */
public class Strings
{
	public static final String	NEWLINE	= System.getProperty("line.separator");
	private static char[]		HEX		= "0123456789ABCDEF".toCharArray();

	/**
	 * Byte array to Hex String
	 */
	public static String toHEX(byte[] input)
	{
		if (input == null) {
			return null;
		}

		int length = input.length;
		char[] output = new char[2 * length];
		for (int i = 0; i < length; ++i) {
			output[2 * i] = HEX[(input[i] & 0xF0) >>> 4];
			output[2 * i + 1] = HEX[input[i] & 0x0F];
		}
		return new String(output);
	}
	
	public static String toHEX(byte[] ...array)
	{
		StringBuilder sb = new StringBuilder();
		for (byte[] bs : array) {
			sb.append(toHEX(bs));
		}		
		return sb.toString();
	}

	/**
	 * Byte to Hex String
	 */
	public static String toHEX(byte input)
	{
		char[] output = new char[2];
		output[0] = HEX[(input & 0xF0) >>> 4];
		output[1] = HEX[input & 0x0F];
		return new String(output);
	}

	/**
	 * Hex String to Byte array
	 */
	public static byte[] fromHEX(String hexInput)
	{
		int length = hexInput.length();
		byte[] output = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			output[i / 2] = (byte) ((Character.digit(hexInput.charAt(i), 16) << 4) + Character.digit(hexInput.charAt(i + 1), 16));
		}
		return output;
	}

	/**
	 * Check is string empty
	 */
	public static boolean isEmpty(String s)
	{
		if (s == null || s.length() == 0)
			return true;

		return false;
	}

	/**
	 * Check i string blank
	 */
	public static boolean isBlank(String s)
	{
		if (s == null || s.length() == 0 || s.trim().length() == 0)
			return true;

		return false;
	}

	/**
	 * Check if string contains [YES;TRUE;1] 
	 */
	public static boolean stringToBool(String s)
	{
		if (s == null)
			return false;

		if (s.equalsIgnoreCase("YES") || s.equalsIgnoreCase("TRUE") || s.equalsIgnoreCase("1"))
			return true;

		return false;
	}

	/**
	 * Format simple string template with arguments 
	 * (replace all '{}' with arguments) 
	 */
	public static String format(String data, String ...args)
	{
		StringBuilder sb = new StringBuilder();

		int index;
		for (int i = 0; i < args.length; i++) 
		{
			index = data.indexOf("{}");			
			if(index == -1)
				break;
			sb.append(data,0,index);
			sb.append(args[i]);
			data = data.substring(index+2);
		}
		
		// add data ... 

		return sb.toString();
	}
	
	
	public static String append(String data, char append, int num)
	{
		final int currLen = data.length();
		final StringBuilder sb = new StringBuilder(num);
		
		for (int i = currLen; i < num; i++) {
			sb.append(append);
		}

		sb.append(data);
		
		return sb.toString();
	}
}
