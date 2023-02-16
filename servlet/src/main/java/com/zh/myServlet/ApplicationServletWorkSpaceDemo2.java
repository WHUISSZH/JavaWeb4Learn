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

@WebServlet("/app2")
public class ApplicationServletWorkSpaceDemo2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        Object nameObj = servletContext.getAttribute("uname");

        System.out.println(nameObj);

    }
}
