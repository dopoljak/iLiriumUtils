package commons;

import com.iLirium.utils.commons.Integers;

import junit.framework.TestCase;

public class Test_Integers extends TestCase
{
	public void test1_byte_size()
	{
		int i = 445;
		byte[] size = Integers.getByteSize(i);
		int out = Integers.getIntSize(size);

		assertEquals(i, out);
	}
	
	public void test2_int_to_bits()
	{
		assertEquals("00000000 00000000 00000000 00000001", Integers.getIntBits(1));
		assertEquals("01111111 11111111 11111111 11111111", Integers.getIntBits(Integer.MAX_VALUE));
		assertEquals("10000000 00000000 00000000 00000000", Integers.getIntBits(Integer.MIN_VALUE));
		
	}
}
