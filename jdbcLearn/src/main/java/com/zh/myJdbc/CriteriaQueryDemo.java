package com.zh.myJdbc;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/10 14:50
 * @description：
 * @modified By：
 * @version:
 */
public class CriteriaQueryDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fruitdb?serverTimezone=Asia/Shanghai&useSSL=false", "root", "zhanghui");

        String querySql = "select * from t_fruit where fid > ?";

        PreparedStatement preparedStatement = connection.prepareStatement(querySql);

        preparedStatement.setInt(1,12);
//        执行查询，返回结果集
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Fruit> fruits = new ArrayList<Fruit>();
        while (resultSet.next()) {
            int fid = resultSet.getInt(1);
            String fname = resultSet.getString("fname");
            int price = resultSet.getInt(3);
            int count = resultSet.getInt(4);
            String remark = resultSet.getString("remark");

            Fruit fruit = new Fruit(fid, fname, price, count, remark);

            fruits.add(fruit);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        fruits.forEach(System.out::println);
    }
}
