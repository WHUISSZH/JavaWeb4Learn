package com.zh.myServlet;

import com.zh.fruit.dataAccseObject.impl.FruitDAOImpl;
import com.zh.fruit.proj.Fruit;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/9 17:24
 * @description： 处理表单通过post方法提交的信息。
 * 我们并没有显式创建addServlet对象去调用doPost()方法，这一过程全部由Tomcat去自动完成
 * @modified By：
 * @version:
 */
public class addServlet extends HttpServlet {
    public addServlet() {
        System.out.println("addServlet 实例化。。。。");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("addServlet 初始化。。。");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        //get方式目前不需要设置编码（ >=tomcat8 ）
        //tomcat8之前的get请求发送的中文数据，要转码
        String fname = request.getParameter("fname");
        //1.将字符串打散成字节数组
        byte[] bytes = fname.getBytes("ISO-8859-1");
        //2.将字节数组按照设定的编码重新组装成字符串
        fname = new String(bytes,"UTF-8");
        */

        //post方式下，设置编码，防止中文乱码
        //需要注意的是，设置编码这一句代码必须在所有的获取参数动作之前
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String countStr = request.getParameter("count");
        int count = Integer.parseInt(countStr);
        String remark = request.getParameter("remark");

        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        boolean flag = fruitDAO.addFruit(new Fruit(0, fname, price, count, remark));

        if (flag) {
            System.out.println("添加成功");
        }else {
            System.out.println("添加失败");
        }
    }
}
