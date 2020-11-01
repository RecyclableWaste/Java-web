package app2.mycipher;

public class Tool {
	public static String getBytes(String str)
	{
		byte[] bytes = str.getBytes();
		String strB = "";
		for(byte b: bytes)
		{
			strB += (b+" ");
		}
		
		return strB;
	}


}
