package datetime;


import com.iLirium.utils.datetime.StopWatch;

import junit.framework.Assert;
import junit.framework.TestCase;

public class Test_StopWatch   extends TestCase
{
	public void test1() throws Exception
	{
		final int milis = 58;
		StopWatch timer = new StopWatch();		
		timer.reset();		
		Thread.sleep(milis);
		
		Assert.assertEquals(timer.getExecutionMilis(), milis, 1);
		
		System.out.println("Execution time: " + timer.getExecutionMilis());
		System.out.println("Execution time: " + timer.getFormatedExecTime());
		
		
		timer.reset();
		Thread.sleep(100);
		System.out.println("Exec time of func was  : " + timer.getFormatedInlineTime());
		
		Thread.sleep(50);
		System.out.println("Exec time of func was  : " + timer.getFormatedInlineTime());
				
		Thread.sleep(333);
		System.out.println("Exec time of func was  : " + timer.getFormatedInlineTime());
		System.out.println("Total time of func was : " + timer.getFormatedExecTime());
		
	}
}
