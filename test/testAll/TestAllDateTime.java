package testAll;

import junit.framework.Test;
import junit.framework.TestSuite;
import datetime.Test_Dates;
import datetime.Test_StopWatch;

public class TestAllDateTime
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("TEST ALL DATETIME");

		// $JUnit-BEGIN$
		suite.addTestSuite(Test_Dates.class);
		suite.addTestSuite(Test_StopWatch.class);
		// $JUnit-END$

		return suite;
	}
}
