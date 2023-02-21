package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/21 10:09
 * @description：
 * @modified By：
 * @version:
 */
@WebFilter("*.do")
public class Filter01 implements Filter {
    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("A");
//        放行
        chain.doFilter(request, response);
        System.out.println("A2");
    }

    public void destroy() {

    }
}
