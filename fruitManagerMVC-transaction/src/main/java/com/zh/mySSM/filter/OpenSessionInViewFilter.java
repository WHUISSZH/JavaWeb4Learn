package com.zh.mySSM.filter;

import com.zh.mySSM.trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/21 15:14
 * @description：
 * @modified By：
 * @version:
 */
@WebFilter("*.do")
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            TransactionManager.beginTrans();
            System.out.println("=======================已开启事务=========================");
            chain.doFilter(request, response);
            TransactionManager.commitTrans();
            System.out.println("=======================提交事务=========================");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                TransactionManager.rollbackTrans();
                System.out.println("=======================捕捉到异常，进行事务回滚=========================");
                ((HttpServletResponse) response).sendRedirect("fruit.do");
                System.out.println("事务回滚完成");
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.out.println("=======================事务回滚出错=========================");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
    }
}
