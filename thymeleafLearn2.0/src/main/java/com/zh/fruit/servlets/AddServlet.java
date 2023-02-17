package com.zh.fruit.servlets;

import com.zh.fruit.dao.impl.FruitDAOImpl;
import com.zh.fruit.prjo.Fruit;
import com.zh.mySSM.mySpringMVC.ViewBaseServlet;
import com.zh.mySSM.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/16 21:51
 * @description：
 * @modified By：
 * @version:
 */
@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");

        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        String countStr = request.getParameter("count");
        String remark = request.getParameter("remark");

        if (StringUtil.isNotEmpty(fname) && StringUtil.isNotEmpty(priceStr) && StringUtil.isNotEmpty(countStr) && StringUtil.isNotEmpty(remark)) {
            int price = Integer.parseInt(priceStr);
            int count = Integer.parseInt(countStr);

            Fruit fruit = new Fruit(0, fname, price, count, remark);

            FruitDAOImpl fruitDAO = new FruitDAOImpl();
            fruitDAO.addFruit(fruit);

            response.sendRedirect("/index");
        }
    }
}
