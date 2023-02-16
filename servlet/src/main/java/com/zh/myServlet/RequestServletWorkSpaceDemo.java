package com.zh.myServlet;

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

@WebServlet("/req")
public class RequestServletWorkSpaceDemo extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      request作用域中保存数据
        request.setAttribute("uname", "zhang");

//        客户端重定向
//        response.sendRedirect("req2");

//        服务器端转发
        request.getRequestDispatcher("req2").forward(request, response);
    }
}
