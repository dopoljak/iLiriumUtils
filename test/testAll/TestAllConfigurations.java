package testAll;

import junit.framework.Test;
import junit.framework.TestSuite;
import configuration.Test_ConfigReader;

public class TestAllConfigurations
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("TEST ALL CONFIGURATION");

		// $JUnit-BEGIN$
		suite.addTestSuite(Test_ConfigReader.class);
		// $JUnit-END$

		return suite;
	}
}
