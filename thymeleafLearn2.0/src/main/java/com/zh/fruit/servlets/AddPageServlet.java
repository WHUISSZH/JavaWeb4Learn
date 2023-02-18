package com.zh.fruit.servlets;

import com.zh.mySSM.mySpringMVC.ViewBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/17 9:22
 * @description：
 * @modified By：
 * @version:
 */
@WebServlet("/addPage")
public class AddPageServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.processTemplate("add", req, resp);
    }
}
