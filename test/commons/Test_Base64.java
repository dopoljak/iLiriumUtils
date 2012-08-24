package commons;

import com.iLirium.utils.commons.Base64;

import junit.framework.TestCase;

public class Test_Base64  extends TestCase
{
	public void test1_encode()
	{
		final String text = "ovo je neki bezvezni text i online konverter je koristen da se dobije result!!!! :P";
		final String result = "b3ZvIGplIG5la2kgYmV6dmV6bmkgdGV4dCBpIG9ubGluZSBrb252ZXJ0ZXIgamUga29yaXN0ZW4gZGEgc2UgZG9iaWplIHJlc3VsdCEhISEgOlA=";		
		final String encoded = Base64.encodeToString(text.getBytes(), false);
		
		assertEquals(result, encoded);
	}
	
	public void test2_decode()
	{
		final String base64 = "YW55IGNhcm5hbCBwbGVhc3VyZS4=";		
		final String result = "any carnal pleasure.";		
		final String decoded = new String(Base64.decode(base64));
		
		assertEquals(result, decoded);
	}
}
