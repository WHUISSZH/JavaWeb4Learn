package com.zh.fruit.servlets;

import com.zh.fruit.dao.impl.FruitDAOImpl;
import com.zh.mySSM.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/16 20:36
 * @description：
 * @modified By：
 * @version:
 */
@WebServlet("/del.do")
public class DelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request携带的fid参数是通过index.js中的delFruit()传递过来的
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            FruitDAOImpl fruitDAO = new FruitDAOImpl();
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);

//            重定向回index.html，不能只写根目录 /  ,不然读不出数据
//            因为做分页和搜索功能时新建了action = index, 只写 / 不能向 IndexServlet发送请求
            response.sendRedirect("/index");
        }
    }
}
