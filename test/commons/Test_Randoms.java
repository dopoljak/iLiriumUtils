package commons;

import com.iLirium.utils.commons.Randoms;

import junit.framework.TestCase;

public class Test_Randoms extends TestCase
{
	public void test1()
	{
		int LENGHT = 5;
		
		String random1 = Randoms.getRandom(LENGHT);
		assertEquals(random1.length(), LENGHT);
		
		String random2 = Randoms.getRandom(LENGHT);
		assertEquals(random2.length(), LENGHT);
		
		assertNotSame(random1, random2);
	}
}
