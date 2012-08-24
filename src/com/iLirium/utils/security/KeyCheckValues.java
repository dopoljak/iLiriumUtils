package com.iLirium.utils.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author dopoljak@gmail.com
 * 
 * This class generates KCV for DES/AES keys (so key can easily be
 * compared)
 * 
 */
public class KeyCheckValues
{
	/**
	 * Generate KCV for 3DES key
	 */
	public static byte[] kcv3DES(byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] binaryZeroes = new byte[64];
		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(binaryZeroes);
		return cipherText;
	}

	/**
	 * Generate KCV for DES key
	 */
	public static byte[] kcvDES(byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] binaryZeroes = new byte[64];
		final SecretKey key = new SecretKeySpec(keyBytes, "DES");
		final Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(binaryZeroes);
		return cipherText;
	}

}
