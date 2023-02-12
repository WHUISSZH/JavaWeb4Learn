package com.zh.myJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/9 21:56
 * @description：
 * @modified By：
 * @version:
 */
public class InsertDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        1. 加载驱动(反射机制）
        Class.forName("com.mysql.cj.jdbc.Driver");
//        2. 通过驱动管理器获取连接对象

//        jdbc:mysql://ip:port/dbname?参数列表
        //如果url中需要带参数，则需要使用?进行连接
        //如果需要带多个参数，则从第二个参数开始使用&连接
        String url = "jdbc:mysql://localhost:3306/fruitdb?serverTimezone=Asia/Shanghai&useSSL=false";
        String user = "root";
        String pwd = "zhanghui";
        Connection connection = DriverManager.getConnection(url, user, pwd);

        System.out.println("connection = " + connection);
//        3. 编写SQL语句
//        fid, fname, price, fcount, remark; fid自增，代码里默认设置为0，插到数据库里会自动更新
        String insertSQL = "insert into t_fruit values(0,?,?,?,?)";
//        4. 创建预处理命令对象
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
//        5. 填充参数，索引从1开始
        preparedStatement.setString(1,"雪莲果");
        preparedStatement.setInt(2,4);
        preparedStatement.setInt(3,100);
        preparedStatement.setString(4,"好吃不贵");

//        6. 执行更新（增删改），返回影响参数
        int c = preparedStatement.executeUpdate();
        System.out.println(c > 0 ? "添加成功"  : "添加失败");
//        7. 释放资源（先关闭preparedStatement， 后关闭connection)
        preparedStatement.close();
        connection.close();
    }
}
