package verifycode;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		/**
		 * 验证验证码
		 * 如果相同向下执行，否则保存到request域，并且转发
		 */
		String session_vcode=(String) request.getSession().getAttribute("session_vcode");
		String verifycode=request.getParameter("verifycode");
		//可以设置忽略大小写
		if(!verifycode.equalsIgnoreCase(session_vcode)) {
			request.setAttribute("msg", "验证码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;//必须打这个，要不后面的代码还会执行。
		}
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		if ("gaoxin".equalsIgnoreCase(name)) {
			Cookie cookie = new Cookie("name",name);
			cookie.setMaxAge(60*60*60*24);
			response.addCookie(cookie);
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			response.sendRedirect(request.getContextPath()+"/succ1.jsp");//记得这样写路径
		}else {
			String msg="用户名或者密码错误";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
