package cas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cas.constants.Constants;

@WebServlet(value= {"/view","/"})
public class ViewController extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute(Constants.LOCAL_SERVICE, request.getParameter(Constants.LOCAL_SERVICE));
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("isLogin")==null || (!session.getAttribute("isLogin").equals("true")))
		{
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
		else
		{
			request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
		}
	}
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request,response);
	}
	
}
