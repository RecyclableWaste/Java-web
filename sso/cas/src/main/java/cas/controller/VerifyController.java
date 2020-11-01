package cas.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cas.cipher.MyCipher;
import cas.constants.Constants;
import cas.constants.Tool;
import cas.loginCacheUtil.LoginCacheUtil;
import cas.verifyTool.Verification;

@WebServlet(value="/ma")
public class VerifyController extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("cas/VerifyController 开始token验证");
	
		String en = request.getParameter(Constants.VERIFY_ARG_NAME);
		String localService = request.getParameter(Constants.LOCAL_SERVICE);
		String sessionIden = request.getParameter("sessionIden");
		if(en==null || localService==null || sessionIden==null)
		{
			return;
		}
		en = en.replace(' ', '+');
		sessionIden = sessionIden.replace(' ', '+');
		String de = MyCipher.decryptString(en);
		String sessionId = MyCipher.decryptString(sessionIden);
		int index = de.indexOf("token=");
		if(index<0 || index>=de.length())
		{
			String result = MyCipher.encryptString(Constants.VERIFY_FAIL);
			OutputStream out = response.getOutputStream();
			out.write(result.getBytes());
			out.close();
		}
		else
		{
			String token = de.substring(index+6);
			String result;
			long time = System.currentTimeMillis();
			if(Verification.verifyToken(token))
			{
				System.out.println("cas/VerifyController token 验证成功");
				result = MyCipher.encryptString(Constants.VERIFY_PASS+"_"+time);
				System.out.println("cas/VerifyController 添加系统: "+localService);
				LoginCacheUtil.addTokenAndUrlAndSession(token, localService, sessionId);
			}
			else
			{
				System.out.println("cas/VerifyController token 验证失败");
				result = MyCipher.encryptString(Constants.VERIFY_FAIL+"_"+time);
			}
			OutputStream out = response.getOutputStream();
			out.write(result.getBytes());
			out.close();
			System.out.println("cas/VerifyController token验证结束");
		}
	}
}
