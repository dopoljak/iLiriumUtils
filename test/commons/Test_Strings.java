package commons;


import java.util.Arrays;

import com.iLirium.utils.commons.Strings;

import junit.framework.TestCase;


public class Test_Strings  extends TestCase
{	
	public void test1_hex2bytes2hex()
	{
		String hexInput = "0000000000000000";
		byte bytes[] = Strings.fromHEX(hexInput);		
		String hexOutput = Strings.toHEX(bytes);

		assertEquals(hexInput, hexOutput);		
	}
	
	public void test2_bytes2hex2bytes()
	{
		byte input[] = "0011dsdd+65fd+5+534+t+554t5gef1g213fplsdfkopksjiv08uvd89h4hr43h9rgudfoiijo*PÆÆŽÆŽÐÆ#E".getBytes();
		String hexInput = Strings.toHEX(input);
		byte output[] = Strings.fromHEX(hexInput);
		
		assertTrue(Arrays.equals(input, output));
	}
	
	
	/*
	public void test3_bytes2hex2bytes()
	{
		byte input[] = Strings.stringToBytesUTFCustom("0011dsdd+65fd+5+534+t+554t5gef1g213fplsdfkopksjiv08uvd89h4hr43h9rgudfoiijo*PÆÆŽÆŽÐÆ#E");
		String hexInput = Strings.toHEX(input);
		byte output[] = Strings.fromHEX(hexInput);
		assertTrue(Arrays.equals(input, output));
	}
	*/
	
	public void test4_IsBlank()
	{
		String input1 = " ";
		assertTrue(Strings.isBlank(input1));
		
		String input2 = "         ";
		assertTrue(Strings.isBlank(input2));
		
		String input3 = "   a      ";
		assertFalse(Strings.isBlank(input3));
				
		String input4 = "54563";
		assertFalse(Strings.isBlank(input4));
		
		String input5 = " 54563";
		assertFalse(Strings.isBlank(input5));
		
		String input6 = "54563 ";
		assertFalse(Strings.isBlank(input6));
		
		String input7 = null;
		assertTrue(Strings.isBlank(input7));
		
		String input8 = "";
		assertTrue(Strings.isBlank(input8));
	}
	
	
	public void test4_IsEmpty()
	{
		String input1 = "";
		assertTrue(Strings.isEmpty(input1));
		
		String input2 = null;
		assertTrue(Strings.isEmpty(input2));
		
		String input3 = "   a      ";
		assertFalse(Strings.isEmpty(input3));
				
		String input4 = " ";
		assertFalse(Strings.isEmpty(input4));
	}
	
	public void test5_FastFormat()
	{
		String text1 = "text1";
		String text2 = "text2";
		String data = "This is some example text = {}, and this is output = {}";
		String finalResult = "This is some example text = " + text1 + ", and this is output = " + text2 + "";
		
		String result = Strings.format(data, text1, text2);
		
		assertEquals(finalResult, result);
		
		
		
	}
}
