package com.zh.fruit.servlets;

import com.zh.fruit.dao.FruitDAO;
import com.zh.fruit.dao.impl.FruitDAOImpl;
import com.zh.fruit.prjo.Fruit;
import com.zh.mySSM.mySpringMVC.ViewBaseServlet;
import com.zh.mySSM.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/17 21:16
 * @description：
 * @modified By：
 * @version:
 */
@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String operate = request.getParameter("operate");

        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        switch (operate) {
            case "index":
                index(request, response);
                break;
            case "directEditPage":
                directEditPage(request, response);
            case "update":
                update(request, response);
                break;
            case "directAddPage":
                directAddPage(request, response);
                break;
            case "add":
                add(request, response);
                break;
            case "del":
                del(request, response);
                break;
            default:
                throw new RuntimeException("operate值非法");
        }
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        设置分页数
        int pageCapcity = 5;
        HttpSession session = request.getSession();

        String pageCapcityStr = request.getParameter("pageCapcity");
        if (StringUtil.isNotEmpty(pageCapcityStr)) {
            int temPageCapcity = Integer.parseInt(pageCapcityStr);
            if (temPageCapcity > 0) {
                pageCapcity = temPageCapcity;
            }
        }
        session.setAttribute("pageCapcity", pageCapcity);

        int pageNo = 1;
        String keyword = null;

        String oper = request.getParameter("oper");
//        oper不为null。说明是从查询按钮点击过来的
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
//            此时，pageNo更新为1，从请求参数中获取 查询关键字 keyword
            pageNo = 1;
            keyword = request.getParameter("keyword");
//            keyword 为null说明没输关键字，那么默认查询所有记录
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {  //说明此处不是直接点击表单查询发送过来的请求（比如点击下面的上一页下一页或者直接在地址栏输入网址）
            String pageNoStr = request.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageNoStr)) {
                pageNo = Integer.parseInt(pageNoStr);
            }

//            如果不是点击搜索按钮发送过来的请求，那么搜索是居于session域中保存的现有的关键字来进行查询，
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        session.setAttribute("pageNo", pageNo);

        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitListByPageNo(keyword, pageNo, pageCapcity);
        //保存到session作用域
        session.setAttribute("fruitList", fruitList);

//        总记录数
        int fruitCount = fruitDAO.getFruitCount4Search(keyword);
//        总页数
        int pageCount = (fruitCount + pageCapcity - 1) / pageCapcity;
        session.setAttribute("pageCount", pageCount);

        super.processTemplate("index", request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        response.sendRedirect("fruit.do");
    }

    private void directEditPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request携带的fid参数是通过index.js中的delFruit()传递过来的
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            FruitDAOImpl fruitDAO = new FruitDAOImpl();
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);

//            重定向回index.html，不能只写根目录 /  ,不然读不出数据
//            因为做分页和搜索功能时新建了action = index, 只写 / 不能向 IndexServlet发送请求
            response.sendRedirect("fruit.do");
        }
    }

    private void directAddPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.processTemplate("add", req, resp);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            response.sendRedirect("fruit.do");
        }
    }
}