package cas.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cas.constants.Constants;
import cas.loginCacheUtil.LoginCacheUtil;
import cas.loginCacheUtil.Pair;
import cas.verifyTool.Verification;

@WebServlet(value="/logout")
public class LogoutController extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("cas/LogoutController 准备注销");
		String localService = request.getParameter(Constants.LOCAL_SERVICE);
		HttpSession session = request.getSession(false);
		if(session!=null)
		{
			System.out.println("cas/LogoutController 准备注销已登录子系统");
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie: cookies)
			{
				if(cookie.getName().equals("token"))
				{
					String token = cookie.getValue();
					if(Verification.verifyToken(token))
					{
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						LoginCacheUtil.tokenAndUser.remove(token);
						Set<Pair<String,String>> urlAndSessions = LoginCacheUtil.tokenAndURLs.get(token);
						if(urlAndSessions!=null)
						{
							for(Pair<String,String> urlAndSession: urlAndSessions)
							{
								System.out.println("cas/LogoutController 开始注销:"+urlAndSession.first);
								String url = urlAndSession.first;
								String sessionId = urlAndSession.second;
								URL httpUrl = new URL(url+"/logout"+"?"+Constants.LOGOUT_ARG_NAME+"=true&sessionId="+sessionId);
								HttpURLConnection con = (HttpURLConnection) httpUrl.openConnection();
								con.setRequestMethod("GET");
								con.setConnectTimeout(5*1000);
								con.connect();
								BufferedReader reader = new BufferedReader(new InputStreamReader(httpUrl.openStream()));
								reader.readLine();
								reader.close();
								System.out.println("cas/LogoutController 注销完成:"+urlAndSession.first);
							}
							//
							System.out.println("cas/LogoutController 所有已登录子系统注销完毕");
							LoginCacheUtil.tokenAndURLs.remove(token);
						}
					}
				}
			}
			session.invalidate();
			System.out.println("cas/LogoutController 注销完成");
		}
		if(Constants.isSubSystem(localService))
		{
			response.sendRedirect(localService);
		}
		else
		{
			response.sendRedirect(Constants.CAS_URL);
		}
	}

}
