package commons;

import com.iLirium.utils.commons.Generics;

import junit.framework.TestCase;

public class Test_Generics  extends TestCase
{
	public void test1()
	{
		String input1 = Generics.notNull("");
		assertEquals(input1, "");
		
		String input2 = Generics.notNull("dasdasdasd");
		assertEquals(input2, "dasdasdasd");
		
		try {
			String input3 = Generics.notNull(null);
			fail("should throw NullPointerException!: " + input3);
		}
		catch (NullPointerException e)
		{
			//assertEquals(expected, actual)
		}
		
	}
}
