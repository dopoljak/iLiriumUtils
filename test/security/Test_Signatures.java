package security;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import junit.framework.TestCase;

import com.iLirium.utils.security.Keys;
import com.iLirium.utils.security.Signatures;

public class Test_Signatures extends TestCase
{
	public void test1() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		KeyPair kp = Keys.generateRSAKeyPair(2048);
		final String message = "ovo je poruka za potpis ....";
		
		byte[] signature = Signatures.signMessageSHA1(kp.getPrivate(), message.getBytes());
		boolean isOK = Signatures.verifySignatureSHA1(kp.getPublic(), message.getBytes(), signature);

		assertTrue(isOK);
	}
}
