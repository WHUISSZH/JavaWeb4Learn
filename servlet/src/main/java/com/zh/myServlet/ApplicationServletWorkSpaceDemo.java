package com.zh.myServlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/15 21:58
 * @description：
 * @modified By：
 * @version:
 */

@WebServlet("/app")
public class ApplicationServletWorkSpaceDemo extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.向application保存作用域保存数据
        //ServletContext : Servlet上下文
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("uname", "zhang");

//        客户端重定向
//        response.sendRedirect("app2");

//        服务器端转发
        request.getRequestDispatcher("app2").forward(request, response);
    }
}
