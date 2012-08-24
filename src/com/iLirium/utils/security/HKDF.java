package com.iLirium.utils.security;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author dopoljak@gmail.com
 * 
 *         HMAC-based Extract-and-Expand Key Derivation Function (HKDF)
 *         http://tools.ietf.org/html/rfc5869
 * 
 */
public final class HKDF
{
	public String	hmacAlgorithm;
	public int		hashLength;

	/**
	 * Create instance of HKDF algorithm
	 * 
	 * @param hmacAlgorithm
	 *            [HmacSHA256(default), hmacSHA384, hmacSHA512, hmacSHA1]
	 * @throws InvalidAlgorithmParameterException
	 */
	public HKDF(String hmacAlgorithm) throws InvalidAlgorithmParameterException
	{
		this.hmacAlgorithm = hmacAlgorithm;

		if (hmacAlgorithm == null || hmacAlgorithm.equalsIgnoreCase("hmacSHA256")) {
			this.hmacAlgorithm = "HmacSHA256";
			this.hashLength = 32;
		}
		else if (hmacAlgorithm.equalsIgnoreCase("hmacSHA384")) {
			this.hashLength = 48;
		}
		else if (hmacAlgorithm.equalsIgnoreCase("hmacSHA512")) {
			this.hashLength = 64;
		}
		else if (hmacAlgorithm.equalsIgnoreCase("hmacSHA1")) {
			this.hashLength = 20;
		}
		else {
			throw new InvalidAlgorithmParameterException("Currently supports only [HmacSHA256(default),HmacSHA384, HmacSHA512, HmacSHA1]");
		}
	}

	/**
	 * Extract key material using original key and salt
	 * 
	 * @param salt
	 * @param inputKeyringMaterial
	 */
	public byte[] extract(byte[] salt, byte[] inputKeyringMaterial) throws NoSuchAlgorithmException, InvalidKeyException
	{
		if (salt == null || salt.length == 0)
			salt = new byte[hashLength];

		Mac mac = Mac.getInstance(hmacAlgorithm);
		mac.init(new SecretKeySpec(salt, hmacAlgorithm));
		mac.update(inputKeyringMaterial);

		return mac.doFinal();
	}

	/**
	 * @param prk
	 *            from step 1
	 * @param info
	 * @param len
	 * @return output keyring material
	 * @throws IOException
	 */
	public byte[] expand(byte[] prk, byte[] info, int length) throws NoSuchAlgorithmException, InvalidKeyException
	{
		Mac mac = Mac.getInstance(hmacAlgorithm);
		mac.init(new SecretKeySpec(prk, hmacAlgorithm));

		int n = (int) Math.ceil(length * 1f / hashLength);
		byte[] result = new byte[n * hashLength];

		for (int i = 0; i < n; i++) {
			byte counter = (byte) (i + 1);
			mac.update(info);
			mac.update(counter);
			byte[] hash = mac.doFinal();
			mac.reset();
			mac.update(hash);
			System.arraycopy(hash, 0, result, i * hashLength, hashLength);
		}

		byte[] finalResult = new byte[length];
		System.arraycopy(result, 0, finalResult, 0, length);
		return finalResult;
	}
}
