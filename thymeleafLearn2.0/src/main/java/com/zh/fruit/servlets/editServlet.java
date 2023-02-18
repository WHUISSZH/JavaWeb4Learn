package com.zh.fruit.servlets;

import com.zh.fruit.dao.impl.FruitDAOImpl;
import com.zh.fruit.prjo.Fruit;
import com.zh.mySSM.mySpringMVC.ViewBaseServlet;
import com.zh.mySSM.util.StringUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/16 14:56
 * @description：
 * @modified By：
 * @version:
 */
@WebServlet("/edit.do")
public class editServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            FruitDAOImpl fruitDAO = new FruitDAOImpl();
            Fruit fruit = fruitDAO.getFruitByID(fid);
            HttpSession session = request.getSession();

            session.setAttribute("fruit", fruit);
            super.processTemplate("edit", request, response);
        }
    }
}
