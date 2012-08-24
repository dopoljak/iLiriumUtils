package testAll;

import security.Test_HKDF;
import security.Test_Signatures;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAllSecurity
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("TEST ALL SECURITY");

		// $JUnit-BEGIN$
		suite.addTestSuite(Test_HKDF.class);
		suite.addTestSuite(Test_Signatures.class);
		// $JUnit-END$

		return suite;
	}
}
