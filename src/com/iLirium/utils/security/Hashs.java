package com.iLirium.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author dopoljak@gmail.com
 */
public class Hashs
{
	/**
	 * Return SHA1 hash from byte array
	 */
	public static byte[] getSHA1(byte[] input) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(input);
		return md.digest();
	}
	
	
	/**
	 * Return SHA2-256 hash from input byte array
	 */
	public static byte[] getSHA256(byte[] input) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(input);
		return md.digest();
	}
}
