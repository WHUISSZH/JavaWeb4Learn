package com.zh.myServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/15 11:21
 * @description：
 * @modified By：
 * @version:
 */
public class SessionServletDemo2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session保存作用域
        Object uname = request.getSession().getAttribute("uname");
        System.out.println(uname);
    }
}
