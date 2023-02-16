package com.zh.myServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/14 21:58
 * @description：
 * @modified By：
 * @version:
 */
public class LifeCycleDemo extends HttpServlet {
    public LifeCycleDemo() {
        System.out.println("==========lifeCycleDemo 实例化===========");
    }

    //    浏览器第一次访问http://localhost:8848/servlet/lifeCycle时，LifeCycleDemo类开始初始化
    @Override
    public void init() throws ServletException {
        System.out.println("==========lifeCycleDemo 初始化===========");
    }

//    公共方法先校验request请求，确认是httpServletRequest请求后再调用protected service方法处理请求
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;

        if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        request = (HttpServletRequest) req;
        response = (HttpServletResponse) res;

        service(request, response);
    }

//    只要访问http://localhost:8848/servlet/lifeCycle，那么就会调用service方法进行服务
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("正在服务。。。。。。");
    }

//    servlet的销毁是随着tomcat容器的关闭而销毁
    @Override
    public void destroy() {
        System.out.println("正在销毁。。。。。");
    }
}
