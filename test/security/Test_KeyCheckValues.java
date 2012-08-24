package security;

import junit.framework.TestCase;

import com.iLirium.utils.commons.Strings;
import com.iLirium.utils.security.KeyCheckValues;

public class Test_KeyCheckValues extends TestCase
{
	public void test1() throws Exception
	{
		byte[] keyBytes = Strings.fromHEX("C11C136BF267BA80");
		byte[] kcv = KeyCheckValues.kcvDES(keyBytes);
		
		assertNotNull(kcv);
	}
	
	public void test2() throws Exception
	{
		byte[] keyBytes = Strings.fromHEX("EA07435E204098DA5461E50D31ECFB9289B592ADDA20492F");		
		byte[] kcv = KeyCheckValues.kcv3DES(keyBytes);
		
		assertNotNull(kcv);
	}
}
