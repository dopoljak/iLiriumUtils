package datetime;


import com.iLirium.utils.datetime.Dates;

import junit.framework.TestCase;

public class Test_Dates extends TestCase
{
	public void test1()
	{
		long miliseconds = 5945123;
		
		String formated1 = Dates.getFormatedTime(miliseconds);
		
		System.out.println("" + formated1);
		
	}
}
