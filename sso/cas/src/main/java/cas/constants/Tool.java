package cas.constants;

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
	public static void main(String[] args)
	{
		System.out.println(Tool.getBytes("sfefe"));
	}
}
