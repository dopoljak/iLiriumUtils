package com.iLirium.utils.commons;

/**
 * @author dopoljak@gmail.com
 */
public class Integers
{
	/**
	 * Get 2Bytes size from integer
	 * 
	 * @param messageLen
	 * @return
	 */
	public static byte[] getByteSize(int messageLen)
	{
		byte byteMsgLen[] = new byte[2];
		byteMsgLen[0] = (byte) ((messageLen >> 8) & 0xFF);
		byteMsgLen[1] = (byte) (messageLen & 0xFF);
		return byteMsgLen;
	}

	/**
	 * Get integer from 2Bytes value
	 * 
	 * @param msgLen
	 * @return
	 */
	public static int getIntSize(byte[] msgLen)
	{
		int low = msgLen[1] & 0xFF;
		int high = msgLen[0] & 0xFF;
		int messageLen = (int) (high << 8 | low);
		return messageLen;
	}

	
	/**
	 * Input Integer value, returns bit representation
	 * @param value
	 * @return
	 */
	public static String getIntBits(int value)
	{
		int displayMask = 1 << 31;
		StringBuilder buffer = new StringBuilder(35);

		for (int c = 1; c <= 32; c++) {
			buffer.append((value & displayMask) == 0 ? '0' : '1');
			value <<= 1;
			if (c % 8 == 0)
				buffer.append(' ');
		}

		return buffer.toString().trim();
	}
}
