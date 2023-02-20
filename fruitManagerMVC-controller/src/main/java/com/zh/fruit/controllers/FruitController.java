package com.zh.fruit.controllers;

import com.zh.fruit.dao.FruitDAO;
import com.zh.fruit.dao.impl.FruitDAOImpl;
import com.zh.fruit.prjo.Fruit;
import com.zh.mySSM.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/17 21:16
 * @description：
 * @modified By：
 * @version:
 */

//controller 不需要webServlet注解
//@WebServlet("/fruit.do")

//因为fruitController的webServlet注解被删了，这个类不会被识别成一个servlet,
//不会被识别成一个servlet就不会用servletContext这个属性，所以就会报空指针错，
//那么现在就需要自己在fruitController类里面自己设置servletContext这个属性，
//并且通过反射给servletContext属性赋值

public class FruitController {
    private String index(String oper, String keyword, Integer pageNo, Integer pageCapcity, HttpServletRequest request) {
//        设置分页数
        HttpSession session = request.getSession();

        if (pageCapcity == null || pageCapcity <= 0) {
            pageCapcity = 5;
        }
        session.setAttribute("pageCapcity", pageCapcity);

        if (pageNo == null) {
            pageNo = 1;
        }

//        oper不为null。说明是从查询按钮点击过来的
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
//            此时，pageNo更新为1，从请求参数中获取 查询关键字 keyword
            pageNo = 1;
//            keyword 为null说明没输关键字，那么默认查询所有记录
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {  //说明此处不是直接点击表单查询发送过来的请求（比如点击下面的上一页下一页或者直接在地址栏输入网址）
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

        return "index";
    }

    private String update(Integer fid, String fname, Integer price, Integer count, String remark) {
//        更新数据库
        Fruit fruit = new Fruit(fid, fname, price, count, remark);
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        fruitDAO.updateFruit(fruit);

//        跳转到主界面
//        需要客户端重定向操作，目的是重新给IndexServlet发请求，重新获取furitList，
//        然后覆盖到session中，这样index.html页面上显示的session中的数据才是最新的
        return "redirect:fruit.do";
    }

    private String directEditPage(Integer fid, HttpServletRequest request) {
        if (fid != null) {
            FruitDAOImpl fruitDAO = new FruitDAOImpl();
            Fruit fruit = fruitDAO.getFruitByID(fid);

            HttpSession session = request.getSession();
            session.setAttribute("fruit", fruit);

            return "edit";
        }
        return "error";
    }

    private String del(Integer fid) {
        if (fid != null) {
            FruitDAOImpl fruitDAO = new FruitDAOImpl();
            fruitDAO.delFruit(fid);

            return "redirect:fruit.do";
        }
        return "error";
    }

    private String directAddPage() {
        return "add";
    }

    private String add(Integer fid, String fname, Integer price, Integer count, String remark) {
        Fruit fruit = new Fruit(0, fname, price, count, remark);

        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        fruitDAO.addFruit(fruit);

        return "redirect:fruit.do";
    }
}