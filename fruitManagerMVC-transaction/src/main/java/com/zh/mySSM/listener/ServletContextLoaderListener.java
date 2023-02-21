package com.zh.mySSM.listener;

import com.zh.mySSM.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/21 21:44
 * @description：
 * @modified By：
 * @version:
 */
@WebListener
public class ServletContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //1. 读取servlet上下文对象
        ServletContext application = servletContextEvent.getServletContext();
        //2. 获取上下文初始化参数
        String configFilePath = application.getInitParameter("contextConfigLocation");
        //3. 创建IOC容器
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(configFilePath);
        //4. 将IOC容器保存到application作用域
        application.setAttribute("beanFactory", beanFactory);
    }
}
