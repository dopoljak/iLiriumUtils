package testAll;


import security.Test_Hashs;
import commons.Test_Base64;
import commons.Test_Generics;
import commons.Test_HexDump;
import commons.Test_Integers;
import commons.Test_Strings;
import datetime.Test_Dates;
import datetime.Test_StopWatch;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAllCommons
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("TEST ALL COMMON");

		// $JUnit-BEGIN$
		suite.addTestSuite(Test_Strings.class);
		suite.addTestSuite(Test_StopWatch.class);
		suite.addTestSuite(Test_Integers.class);
		suite.addTestSuite(Test_HexDump.class);
		suite.addTestSuite(Test_Hashs.class);
		suite.addTestSuite(Test_Base64.class);
		suite.addTestSuite(Test_Dates.class);
		suite.addTestSuite(Test_Generics.class);
		// $JUnit-END$

		return suite;
	}
}
