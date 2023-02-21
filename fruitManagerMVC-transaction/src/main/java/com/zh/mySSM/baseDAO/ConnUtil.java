package com.zh.mySSM.baseDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/21 14:55
 * @description：
 * @modified By：
 * @version:
 */
public class ConnUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/fruitdb?serverTimezone=Asia/Shanghai&useSSL=false";
    private static final String USER = "root";
    private static final String PWD = "zhanghui";

    /**
     * 创建数据库连接
     *
     * @return 连接对象
     */
    public static Connection createConn() {
        //  1. 加载驱动
        try {
            Class.forName(DRIVER);
            //  2. 建立数据库连接
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从threadLocal中获取数据库连接
     *
     * @return
     */
    public static Connection getConn() {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = createConn();
            threadLocal.set(connection);
        }
        return connection;
    }


    /**
     * 关闭数据库连接
     */
    public static void closeConn() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            return;
        }
        if (connection.isClosed()) {
            connection.close();
            threadLocal.set(null);
        }
    }
}
