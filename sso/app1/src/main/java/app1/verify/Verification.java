package app1.verify;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import app1.mycipher.MyCipher;
import app1.mycipher.Tool;
import cas.constants.Constants;



public class Verification {

	public static boolean verifyFromCAS(String CASVerifyURL, String token, String localService, String sessionId) throws Exception
	{
		long beginTime = System.currentTimeMillis();
		//发送验证
		String en = MyCipher.encryptString("token="+token);
		String sessionIden = MyCipher.encryptString(sessionId);
		URL url = new URL(CASVerifyURL+"?"+Constants.VERIFY_ARG_NAME+"="+en
				+"&"+Constants.LOCAL_SERVICE+"="+localService
				+"&sessionIden="+sessionIden);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(5*1000);
		con.connect();
		//接收验证
		InputStream in = con.getInputStream();
		StringBuffer enResult = new StringBuffer();
		byte[] data = new byte[1024];
		int length = 0;
		while((length=in.read(data))!=-1)
		{
			String s = new String(data,0,length);
			enResult.append(s);
		}
		in.close();
		con.disconnect();
		//处理，判断验证结果
		String deResult = MyCipher.decryptString(enResult.toString());
		String passOrFail = deResult.split("_")[0];
		long time = Long.parseLong(deResult.split("_")[1]);
		if(passOrFail.equals(Constants.VERIFY_PASS) && (beginTime<time && time<System.currentTimeMillis()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
