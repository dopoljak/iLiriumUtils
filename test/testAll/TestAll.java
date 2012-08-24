package testAll;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAll
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("TEST ALL");

		// $JUnit-BEGIN$
		suite.addTest(TestAllCommons.suite());
		suite.addTest(TestAllDateTime.suite());
		suite.addTest(TestAllHttp.suite());		
		suite.addTest(TestAllSecurity.suite());
		suite.addTest(TestAllSystem.suite());
		// $JUnit-END$

		return suite;
	}
}
