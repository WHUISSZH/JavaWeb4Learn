package com.zh.fruit.servlets;

import com.zh.fruit.dao.impl.FruitDAOImpl;
import com.zh.fruit.prjo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/16 15:51
 * @description：
 * @modified By：
 * @version:
 */
@WebServlet("/update.do")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        设置编码
        request.setCharacterEncoding("utf-8");

//        获取form表单传递来的参数
        String fidStr = request.getParameter("fid");
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        String countStr = request.getParameter("count");
        String remark = request.getParameter("remark");

        int fid = Integer.parseInt(fidStr);
        int price = Integer.parseInt(priceStr);
        int count = Integer.parseInt(countStr);

//        更新数据库
        Fruit fruit = new Fruit(fid, fname, price, count, remark);
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        fruitDAO.updateFruit(fruit);

//        跳转到主界面
//        需要客户端重定向操作，目的是重新给IndexServlet发请求，重新获取furitList，
//        然后覆盖到session中，这样index.html页面上显示的session中的数据才是最新的
        response.sendRedirect("/index");
    }
}
