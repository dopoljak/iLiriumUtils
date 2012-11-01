package commons;

import com.iLirium.utils.commons.Assert;

import junit.framework.TestCase;

public class Test_Generics  extends TestCase
{
	public void test1()
	{
		String input1 = Assert.notNull("");
		assertEquals(input1, "");
		
		String input2 = Assert.notNull("dasdasdasd");
		assertEquals(input2, "dasdasdasd");
		
		try {
			String input3 = Assert.notNull(null);
			fail("should throw NullPointerException!: " + input3);
		}
		catch (NullPointerException e)
		{
			//assertEquals(expected, actual)
		}
		
	}
}
