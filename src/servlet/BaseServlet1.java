package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;

public abstract class BaseServlet1 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");//处理响应编码
		request.setCharacterEncoding("UTF-8");
		// 参数与异常要与要调用的方法一致
		String method = request.getParameter("method");// 获得参数传过来的
		Class class1 = this.getClass();// 获得本类
		Method method1 = null;// try-catch中的变量是局部变量取不出来，应该写在外边申明
		/**
		 * 完善代码的健壮性 1，判断传过来的方法是否为空
		 */
		if (method == null || method.trim().isEmpty()) {
			throw new RuntimeException("你没有传递method参数，无法确定你想干什么");
		}
		/**
		 * 对获取的字符串进行判断。
		 * 
		 */

		/**
		 * 将方法存到Method类的实例中
		 */
		try {
			method1 = class1.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("匹配不到你想要的方法");
		}

		try {
			method1.invoke(this, request, response);// 调用封装在method1中的方法
		} catch (Exception e) {
			System.out.println("您调用的方法：　它内部抛出了异常！");
			throw new RuntimeException(e);
		} 
		/*
		 * 补充一下通过获得的字符串来分析，并且根据分析结果执行东西。
		 *
		try {
			String string = (String) method1.invoke(this, request, response);// 调用封装在method1中的方法
			// 判斷是否含有：
			String after=null;
			if (string.contains(":")) {
				// 读出：号的位置 123:assd
				
				int i = string.indexOf(":");
				// 截取出前缀表示操作
				String front = string.substring(0, i);
				// 截出后缀表示路径
				 after = string.substring(i + 1);
				if (front.equalsIgnoreCase("f")) {
					request.getRequestDispatcher(after).forward(request, response);
				} else if (front.equalsIgnoreCase("r")) {
					response.sendRedirect(request.getContextPath() + after);
				} else {
					throw new RuntimeException("你指定的操作： 当前版本还不支持！");
				}
			} else {// 没有冒号，默认为转发！
				request.getRequestDispatcher(after).forward(request, response);
			}
		} catch (Exception e) {

			throw new RuntimeException();
		}*/
	}

}
