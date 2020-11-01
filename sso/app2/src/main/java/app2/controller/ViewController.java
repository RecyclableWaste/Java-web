package app2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cas.constants.Constants;

@WebServlet(value= {"/view","/","/verifyFail"})
public class ViewController extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		
		String url = request.getRequestURL().toString();
		System.out.println("app2/ViewController url: "+url);
		if(url.endsWith("/verifyFail"))
		{
			request.setAttribute(Constants.LOCAL_SERVICE, Constants.APP1_URL);
			request.getRequestDispatcher("WEB-INF/jsp/verifyFail.jsp").forward(request, response);
		}
		else
		{
			HttpSession session = request.getSession(false);
			if(session==null||session.getAttribute("isLogin")==null||!(session.getAttribute("isLogin").equals("true")))
			{
				request.getRequestDispatcher("/login").forward(request, response);
			}
			else
			{
				request.setAttribute("uname", request.getSession().getAttribute("uname"));
				request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
			}
		}
	}
}