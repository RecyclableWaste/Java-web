package app2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app2.sessionMap.SessionMap;
import cas.constants.Constants;


@WebServlet(value="/logout")
public class LogoutController extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("app2/LogoutController 准备注销");
		String casLogout = request.getParameter(Constants.LOGOUT_ARG_NAME);
		String sessionId = request.getParameter("sessionId");
		
		if(casLogout!=null && casLogout.equals("true"))
		{
			if(sessionId!=null)
			{
				HttpSession session = SessionMap.sessionMap.get(sessionId);
				if(session!=null)
				{
					System.out.println("app2/LogoutController 开始注销");
					session.invalidate();
					System.out.println("app2/LogoutController 开始注销");
				}
			}
			
		}
		else
		{
			System.out.println("app2/LogoutController 去cas注销");
			response.sendRedirect(Constants.CAS_LOGOUT_URL+"?"+Constants.LOCAL_SERVICE+"="+Constants.APP2_URL);
		}
		
	}

}

