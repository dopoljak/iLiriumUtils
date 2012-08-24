package com.iLirium.utils.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * @author dopoljak@gmail.com
 */
public class Signatures
{
	/**
	 * Check signature using Public key and SHA1 hash algorithm
	 */
	public static boolean verifySignatureSHA1(PublicKey publicKey, byte[] message, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		Signature checkSign = Signature.getInstance("SHA1withRSA");
		checkSign.initVerify(publicKey);
		checkSign.update(message);
		boolean verify = checkSign.verify(signature);
		return verify;
	}

	/**
	 * return signature in RSAASSA-PKCS1-v1.5 calculate  and SHA1 hash algorithm
	 */
	public static byte[] signMessageSHA1(PrivateKey secredKey, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		Signature signer = Signature.getInstance("SHA1withRSA");
		signer.initSign(secredKey);
		signer.update(message);
		byte[] signature = signer.sign();
		return signature;
	}
}
