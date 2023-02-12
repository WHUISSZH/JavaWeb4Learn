package com.zh.myJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/10 14:05
 * @description：
 * @modified By：
 * @version:
 */
public class UpdataDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Fruit fruit = new Fruit(35, "蓝莓", "美味");

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fruitdb?useSSL=false&serverTimezone=Asia/Shanghai","root", "zhanghui");

        String updateSql = "update t_fruit set fname = ? , remark = ? where fid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateSql);

        preparedStatement.setString(1, fruit.getFname());
        preparedStatement.setString(2, fruit.getRemark());
        preparedStatement.setInt(3, fruit.getFid());

        int c = preparedStatement.executeUpdate();
        System.out.println(c > 0 ? "修改成功" : "修改失败");

        preparedStatement.close();
        connection.close();
    }
}
