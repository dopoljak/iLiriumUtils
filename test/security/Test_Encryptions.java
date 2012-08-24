package security;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import junit.framework.TestCase;

import com.iLirium.utils.security.Encryptions;
import com.iLirium.utils.security.Keys;

public class Test_Encryptions extends TestCase
{

	public void test1() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException
	{
		KeyPair kp = Keys.generateRSAKeyPair(2048);
		byte[] data = "test_data_211dca23r23rfasd".getBytes();

		byte[] encrypted = Encryptions.encryptRSA(kp.getPublic(), data);
		byte[] decrypted = Encryptions.decryptRSA(kp.getPrivate(), encrypted);
		
		assertEquals(new String(data), new String(decrypted));
	}
	
	
	public void test2() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException
	{
		KeyPair kp = Keys.generateRSAKeyPair(2048);
		byte[] data = "test_data_211dca23r23rfasd".getBytes();

		byte[] encrypted = Encryptions.encryptRSAPKCSv20(kp.getPublic(), data);
		byte[] decrypted = Encryptions.decryptRSAPKCSv20(kp.getPrivate(), encrypted);
		
		assertEquals(new String(data), new String(decrypted));
	}
}
