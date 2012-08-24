package com.iLirium.utils.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author dopoljak@gmail.com
 */
public class Keys
{
	/**
	 * Generate new 3DES SecretKey
	 **/
	public static SecretKey generate3DESKey() throws NoSuchAlgorithmException
	{
		KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
		SecretKey secretKey = keyGen.generateKey();
		return secretKey;
	}

	/**
	 * Generate new AES SecretKey
	 */
	public static SecretKey generateAESKey()  throws NoSuchAlgorithmException
	{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecretKey secretKey = keyGen.generateKey();
		return secretKey;
	}

	/**
	 * Generate RSA KeyPair
	 */
	public static KeyPair generateRSAKeyPair(int size) throws NoSuchAlgorithmException
	{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(size);
		return kpg.generateKeyPair();
	}
	
	/**
	 * Get PublicKey from RSAPublicKey
	 */
	public static PublicKey getPublicKeyFromRSAPublicKey(RSAPublicKey rsaKey) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		KeyFactory rsaFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec rsaKeyspec = new RSAPublicKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent());
		PublicKey key = rsaFactory.generatePublic(rsaKeyspec);
		return key;
	}
}
