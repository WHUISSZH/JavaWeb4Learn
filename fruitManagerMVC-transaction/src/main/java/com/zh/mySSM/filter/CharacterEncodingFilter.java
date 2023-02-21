package com.zh.mySSM.filter;

import com.zh.mySSM.util.StringUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/21 11:18
 * @description：
 * @modified By：
 * @version:
 */
@WebFilter(urlPatterns = {"*.do"}, initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class CharacterEncodingFilter implements Filter {
    private String characterEncoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        String encodingStr = filterConfig.getInitParameter("encoding");
        if (StringUtil.isNotEmpty(encodingStr)) {
            characterEncoding = encodingStr;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(characterEncoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
