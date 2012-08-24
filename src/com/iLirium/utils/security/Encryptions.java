package com.iLirium.utils.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author dopoljak@gmail.com
 */
public class Encryptions
{
	/**
	 * Encrypt some data using RAS encryption algorithm & public key
	 */
	public static byte[] encryptRSA(PublicKey pubKey, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");	// ~ "RSA"
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}

	
	/**
	 * Decrypt some data using RSA decryption algorithm & private key
	 */
	public static byte[] decryptRSA(PrivateKey privKey, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException
	{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * Encrypt some data using RAS PKCS#1 v2.0 padding
	 */
	public static byte[] encryptRSAPKCSv20(PublicKey pubKey, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}
	
	
	
	/**
	 * Decrypt data using PKCS#1 v2.0 padding 
	 */
	public static byte[] decryptRSAPKCSv20(PrivateKey privKey, byte[] data) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		/*
		Optimal Asymmetric Encryption Padding (OAEP) was introduced in PKCS#1 v2.0
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding", "BC");				
		*/
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		return cipher.doFinal(data);
	}

}
