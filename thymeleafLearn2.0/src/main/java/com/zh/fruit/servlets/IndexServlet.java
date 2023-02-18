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
 * @date ：Created in 2023/2/15 16:19
 * @description：
 * @modified By：
 * @version:
 */
//Servlet从3.0版本开始支持注解方式的注册
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

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
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        int pageNo = 1;
        int pageCapcity = 5;
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

//        必须判断是否为空，否则第一次访问index.html时，pageCapcityObj为空
        Object pageCapcityObj = session.getAttribute("pageCapcity");
        if (pageCapcityObj != null) {
            pageCapcity = (Integer) pageCapcityObj;
        }

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


    /**
     * 分页展示所有记录
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
//    @Override
//    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException, ServletException {
//        int pageNo = 1;
//
//        String pageNoStr = request.getParameter("pageNo");
//        if (StringUtil.isNotEmpty(pageNoStr)){
//            pageNo = Integer.parseInt(pageNoStr);
//        }
//
//        HttpSession session = request.getSession();
//        session.setAttribute("pageNo", pageNo);
//
//        FruitDAO fruitDAO = new FruitDAOImpl();
//        List<Fruit> fruitList = fruitDAO.getFruitListByPageNo2(pageNo, 5);
//        //保存到session作用域
//        session.setAttribute("fruitList",fruitList);
//
////        总记录数
//        int fruitCount = fruitDAO.getFruitCount();
////        总页数
//        int pageCount = (fruitCount + 5 - 1) / 5;
//        session.setAttribute("pageCount", pageCount);
//
//        super.processTemplate("index",request,response);
//    }


//    全量查询，没做分页
//    @Override
//    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException, ServletException {
//        FruitDAO fruitDAO = new FruitDAOImpl();
//        List<Fruit> fruitList = fruitDAO.getFruitList();
//        //保存到session作用域
//        HttpSession session = request.getSession() ;
//        session.setAttribute("fruitList",fruitList);
//        //此处的视图名称是 index
//        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
//        //逻辑视图名称 ：   index
//        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
//        //所以真实的视图名称是：      /       index       .html
//        super.processTemplate("index",request,response);
//    }
}
