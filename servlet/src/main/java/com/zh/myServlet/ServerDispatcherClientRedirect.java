package com.zh.myServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/15 14:24
 * @description：
 * @modified By：
 * @version:
 */
public class ServerDispatcherClientRedirect extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("我是servletA，内部转发给servletB，由它来提供服务");
//        服务器内部转发，里面的参数是web.xml中填的url-patten
//        req.getRequestDispatcher("session").forward(req,resp);

//        客户端重定向
        resp.sendRedirect("session");
    }
}
