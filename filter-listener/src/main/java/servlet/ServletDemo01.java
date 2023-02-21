package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/21 10:05
 * @description：
 * @modified By：
 * @version:
 */
@WebServlet("/demo01.do")
public class ServletDemo01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("demo01 service..........................");
        request.getRequestDispatcher("index.html").forward(request, response);
    }
}
