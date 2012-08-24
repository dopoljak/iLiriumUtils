package commons;




import com.iLirium.utils.commons.HexDump;

import junit.framework.TestCase;

public class Test_HexDump extends TestCase
{
	public void test1()
	{		
		byte[] bytes = "65dsa5615fa65gf654gfd654šèlpšlpšl342šlpšfdšoðkfd0'dfk'fdsjjisfdjiodfjsiodajff0hsdi0fhdsifhsdi0fhsidpfisdhfisdhfpisdfihjpdapoj".getBytes();
		String hexDump = HexDump.dump(bytes);		
		System.out.println("HEX DUMP:" + hexDump);		
	}
}
