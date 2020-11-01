package app1.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app1.sessionMap.SessionMap;
import app1.verify.Verification;
import cas.constants.Constants;

@WebServlet(value="/login")
public class LoginController extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{	
		System.out.println("app1/LoginController begin");
		HttpSession session = request.getSession();
		String isLogin = null;
		if(session!=null)
		{
			isLogin = (String) session.getAttribute("isLogin");
		}
		//
		if(isLogin!=null && isLogin.equals("true"))
		{
			System.out.println("app1/LoginController 已登录用户");
			request.getRequestDispatcher("/view").forward(request, response);
		}
		else
		{
			System.out.println("app1/LoginController 未登录用户");
			Cookie[] cookies = request.getCookies();
			String token = null;
			if(cookies!=null)
			{
				for(Cookie cookie: cookies)
				{
					System.out.println("app1/LoginController cookies: "+cookie.getName()+":"+cookie.getValue());
					//有token,去cas验证token
					if(cookie.getName().equals("token"))
					{
						token = cookie.getValue();
						try {
							System.out.println("app1/LoginController 有token, 去CAS验证");
							if(Verification.verifyFromCAS(Constants.VERIFY_URL, token,Constants.APP1_URL,request.getSession().getId()))
							{
								System.out.println("app1/LoginController 验证成功 保存session: "+request.getSession().getId());
								request.getSession().setAttribute("isLogin", "true");
								SessionMap.sessionMap.put(request.getSession().getId(),request.getSession());
								request.getSession().setAttribute("uname", request.getParameter("uname"));
								request.getRequestDispatcher("/view").forward(request, response);
							}
							else
							{
								//token 验证失败
								request.getSession().invalidate();
								response.sendRedirect(Constants.CAS_LOGIN_URL+"?"+Constants.LOCAL_SERVICE+"="+Constants.APP1_URL);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					}
				}
			}
			//没有token,去cas登录
			System.out.println("app1/LoginController 没有token,去CAS登录");
			String localURLArg = "?"+Constants.LOCAL_SERVICE+"="+Constants.APP1_URL;
			response.sendRedirect(Constants.CAS_LOGIN_URL+localURLArg);
		}
	}
	
}
