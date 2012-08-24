package security;


import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.iLirium.utils.commons.Strings;
import com.iLirium.utils.security.HKDF;

import junit.framework.TestCase;

/**
 * @author dopoljak@gmail.com
 *
 */
public class Test_HKDF extends TestCase
{
	public Test_HKDF()
	{
	}

	@Override
	public void setUp()
	{
	}

	@Override
	public void tearDown()
	{
	}

	/**
	 * Basic test case with SHA-256
	 * @throws IOException 
	 * @throws InvalidAlgorithmParameterException 
	 */
	public void testVector1_SHA256() throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
	{
		final String ikmHEX = "0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b";
		final String saltHEX = "000102030405060708090a0b0c";
		final String infoHEX = "f0f1f2f3f4f5f6f7f8f9";
		final int L = 42;
		
		byte[] ikm = Strings.fromHEX(ikmHEX);
		byte[] salt = Strings.fromHEX(saltHEX);
		byte[] info = Strings.fromHEX(infoHEX);
		
		HKDF hkdf = new HKDF("HMacSHA256");
		byte[] pseudoRandomKey = hkdf.extract(salt, ikm);
		byte[] outputKeyingMaterial = hkdf.expand(pseudoRandomKey, info, L);

		assertEquals("077709362C2E32DF0DDC3F0DC47BBA6390B6C73BB50F9C3122EC844AD7C2B3E5", Strings.toHEX(pseudoRandomKey));
		assertEquals("3CB25F25FAACD57A90434F64D0362F2A2D2D0A90CF1A5A4C5DB02D56ECC4C5BF34007208D5B887185865", Strings.toHEX(outputKeyingMaterial));

	}
	
	
	/**
	 * Test with SHA-256 and longer inputs/outputs
	 * @throws InvalidAlgorithmParameterException 
	 */
	public void testVector2_SHA256() throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
	{
		final String ikmHEX = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f";
		final String saltHEX = "606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3a4a5a6a7a8a9aaabacadaeaf";
		final String infoHEX = "b0b1b2b3b4b5b6b7b8b9babbbcbdbebfc0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedfe0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfeff";
		final int L = 82;
		
		byte[] ikm = Strings.fromHEX(ikmHEX);
		byte[] salt = Strings.fromHEX(saltHEX);
		byte[] info = Strings.fromHEX(infoHEX);
		
		HKDF hkdf = new HKDF("HMacSHA256");
		byte[] pseudoRandomKey = hkdf.extract(salt, ikm);
		byte[] outputKeyingMaterial = hkdf.expand(pseudoRandomKey, info, L);
		
		assertEquals("06A6B88C5853361A06104C9CEB35B45CEF760014904671014A193F40C15FC244", Strings.toHEX(pseudoRandomKey));
		assertEquals("B11E398DC80327A1C8E7F78C596A49344F012EDA2D4EFAD8A050CC4C19AFA97C59045A99CAC7827271CB41C65E590E09DA3275600C2F09B8367793A9ACA3DB71CC30C58179EC3E87C14C01D5C1F3434F1D87", Strings.toHEX(outputKeyingMaterial));
	}
	
	
	/**
	 *  Test with SHA-256 and zero-length salt/info
	 * @throws InvalidAlgorithmParameterException 
	 */
	public void testVector3_SHA256() throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
	{
		final String ikmHEX = "0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b";
		final int L = 42;
		
		byte[] ikm = Strings.fromHEX(ikmHEX);
		byte[] salt = null;
		byte[] info = null;
		
		HKDF hkdf = new HKDF("HMacSHA256");
		byte[] pseudoRandomKey = hkdf.extract(salt, ikm);
		byte[] outputKeyingMaterial = hkdf.expand(pseudoRandomKey, info, L);
		
		assertEquals("19EF24A32C717B167F33A91D6F648BDF96596776AFDB6377AC434C1C293CCB04", Strings.toHEX(pseudoRandomKey));
		assertEquals("8DA4E775A563C18F715F802A063C5A31B8A11F5C5EE1879EC3454E5F3C738D2D9D201395FAA4B61A96C8", Strings.toHEX(outputKeyingMaterial));
	}
	
	
	/**
	 *  Basic test case with SHA-1
	 * @throws InvalidAlgorithmParameterException 
	 */
	public void testVector4_SHA1() throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
	{
		final String ikmHEX = "0b0b0b0b0b0b0b0b0b0b0b";
		final String saltHEX = "000102030405060708090a0b0c";
		final String infoHEX = "f0f1f2f3f4f5f6f7f8f9";
		final int L = 42;

		byte[] ikm = Strings.fromHEX(ikmHEX);
		byte[] salt = Strings.fromHEX(saltHEX);
		byte[] info = Strings.fromHEX(infoHEX);
		
		HKDF hkdf = new HKDF("HMacSHA1");
		byte[] pseudoRandomKey = hkdf.extract(salt, ikm);
		byte[] outputKeyingMaterial = hkdf.expand(pseudoRandomKey, info, L);
		
		assertEquals("9B6C18C432A7BF8F0E71C8EB88F4B30BAA2BA243", Strings.toHEX(pseudoRandomKey));
		assertEquals("085A01EA1B10F36933068B56EFA5AD81A4F14B822F5B091568A9CDD4F155FDA2C22E422478D305F3F896", Strings.toHEX(outputKeyingMaterial));
	}
	
	/**
	 *  Test with SHA-1 and longer inputs/outputs
	 * @throws InvalidAlgorithmParameterException 
	 */
	public void testVector5_SHA1() throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
	{
		final String ikmHEX = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f";
		final String saltHEX = "606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3a4a5a6a7a8a9aaabacadaeaf";
		final String infoHEX = "b0b1b2b3b4b5b6b7b8b9babbbcbdbebfc0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedfe0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfeff";
		final int L = 82;

		byte[] ikm = Strings.fromHEX(ikmHEX);
		byte[] salt = Strings.fromHEX(saltHEX);
		byte[] info = Strings.fromHEX(infoHEX);
		
		HKDF hkdf = new HKDF("HMacSHA1");
		byte[] pseudoRandomKey = hkdf.extract(salt, ikm);
		byte[] outputKeyingMaterial = hkdf.expand(pseudoRandomKey, info, L);
		
		assertEquals("8ADAE09A2A307059478D309B26C4115A224CFAF6", Strings.toHEX(pseudoRandomKey));
		assertEquals("0BD770A74D1160F7C9F12CD5912A06EBFF6ADCAE899D92191FE4305673BA2FFE8FA3F1A4E5AD79F3F334B3B202B2173C486EA37CE3D397ED034C7F9DFEB15C5E927336D0441F4C4300E2CFF0D0900B52D3B4", Strings.toHEX(outputKeyingMaterial));
	}
	
	/**
	 *  Test with SHA-1 and zero-length salt/info
	 * @throws InvalidAlgorithmParameterException 
	 */
	public void testVector6_SHA1() throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
	{
		final String ikmHEX = "0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b";
		final int L = 42;

		byte[] ikm = Strings.fromHEX(ikmHEX);
		byte[] salt = null;
		byte[] info = null;
		
		HKDF hkdf = new HKDF("HMacSHA1");
		byte[] pseudoRandomKey = hkdf.extract(salt, ikm);
		byte[] outputKeyingMaterial = hkdf.expand(pseudoRandomKey, info, L);
		
		assertEquals("DA8C8A73C7FA77288EC6F5E7C297786AA0D32D01", Strings.toHEX(pseudoRandomKey));
		assertEquals("0AC1AF7002B3D761D1E55298DA9D0506B9AE52057220A306E07B6B87E8DF21D0EA00033DE03984D34918", Strings.toHEX(outputKeyingMaterial));
	}
	
	/**
	 *   Test with SHA-1, salt not provided (defaults to HashLen zero octets), zero-length info
	 * @throws InvalidAlgorithmParameterException 
	 */
	public void testVector7_SHA1() throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
	{
		final String ikmHEX = "0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c";
		final int L = 42;

		byte[] ikm = Strings.fromHEX(ikmHEX);
		byte[] salt = null;
		byte[] info = null;
		
		HKDF hkdf = new HKDF("HMacSHA1");
		byte[] pseudoRandomKey = hkdf.extract(salt, ikm);
		byte[] outputKeyingMaterial = hkdf.expand(pseudoRandomKey, info, L);
		
		assertEquals("2ADCCADA18779E7C2077AD2EB19D3F3E731385DD", Strings.toHEX(pseudoRandomKey));
		assertEquals("2C91117204D745F3500D636A62F64F0AB3BAE548AA53D423B0D1F27EBBA6F5E5673A081D70CCE7ACFC48", Strings.toHEX(outputKeyingMaterial));
	}
}
