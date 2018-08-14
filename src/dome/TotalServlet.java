package dome;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TotalServlet
 */
@WebServlet("/TotalServlet")
public class TotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TotalServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//写一个统计网站访问量的东西。
		//1，得到servletcontext对象，注意记得随时getsetAttribute
		/**
		 * null只对对象和string,Integer有用,基本类型没有null.
		 */
		Integer count=(Integer) this.getServletContext().getAttribute("count");
		if(count==null) {
			count=1;
			this.getServletContext().setAttribute("count", count);
		
		}else {
			count=count+1;
			this.getServletContext().setAttribute("count",count);
		}
		response.getWriter().print(count);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
