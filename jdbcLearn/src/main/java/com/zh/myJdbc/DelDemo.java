package com.zh.myJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/10 14:30
 * @description：
 * @modified By：
 * @version:
 */
public class DelDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fruitdb?serverTimezone=Asia/Shanghai&useSSL=false", "root", "zhanghui");

        String delSql = "delete from t_fruit where fid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(delSql);
        
        preparedStatement.setInt(1, 35);

        int i = preparedStatement.executeUpdate();

        System.out.println(i > 0 ? "删除成功" : "删除失败");

        preparedStatement.close();
        connection.close();
    }
}
