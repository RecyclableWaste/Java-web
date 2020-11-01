package cas.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cas.constants.Constants;
import cas.database.UserDAO;
import cas.domain.User;
import cas.loginCacheUtil.LoginCacheUtil;

@WebServlet(value= {"/login"})
public class LoginController extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		request.setAttribute(Constants.LOCAL_SERVICE,request.getParameter(Constants.LOCAL_SERVICE));
		request.getRequestDispatcher("/view").forward(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		String inputName = request.getParameter("name");
		String inputPasswd = request.getParameter("passwd");
		String localservice = (String) request.getParameter(Constants.LOCAL_SERVICE);
		
		User user = new User(inputName,inputPasswd);
		if(UserDAO.findUser(user))
		{
			System.out.println("cas/LoginController 登录成功 添加用户 设置token");
			String token = UUID.randomUUID().toString();
			LoginCacheUtil.tokenAndUser.put(token,user);
			Cookie cookie = new Cookie("token",token);
			cookie.setPath("/");
			response.addCookie(cookie);
			request.getSession().setAttribute("uname", user.getName());
			request.getSession().setAttribute("isLogin", "true");
			if(Constants.isSubSystem(localservice))
			{
				System.out.println("cas/LoginController 返回子系统:"+localservice);
				response.sendRedirect(localservice+"?"+Constants.USER_ARG_NAME+"="+user.getName());
			}
			else
			{
				response.sendRedirect("view");
			}
			
		}
		else
		{
			System.out.println("cas/LoginController 登录失败");
			request.setAttribute("msg", "登录失败");
			request.getRequestDispatcher("/").forward(request, response);
		}
		
	}
	
}
