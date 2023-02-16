package com.zh.myJdbc.dbConnectionPool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/13 21:36
 * @description： Druit数据库连接池：连接池就是一个容器，连接池中保存了一些数据库连接，这些链接是可以重复使用的。
 * 关闭连接的时候不是真正的关闭，而是将连接对象放回线程池中，等再次需要的时候再拿出来使用。减少了资源的消耗，提高了效率
 * @modified By：
 * @version:
 */
public class DruidDemo {
    public static void main(String[] args) throws IOException, SQLException {
        useDruidByPropertiesFile();
    }

    /**
     * 简单使用Druid实现数据库连接池
     */
    public static void simpleUseDruid() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/fruitdb?useSSL=false&serverTimezone=Asia/Shanghai");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("zhanghui");

        druidDataSource.setInitialSize(3);
        druidDataSource.setMaxActive(5);
        druidDataSource.setMaxWait(3000);

        for (int i = 0; i < 10; i++) {
            Connection connection = druidDataSource.getConnection();
            System.out.println(i + "----------" + connection);
        }
    }

    /**
     * 从配置文件中读取数据库配置
     * @throws IOException
     * @throws SQLException
     */
    public static void useDruidByPropertiesFile() throws IOException, SQLException {
        Properties properties = new Properties();

        // 有bug，resourceAsStream一直为null
        InputStream resourceAsStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        properties.load(resourceAsStream);

        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
        druidDataSource.setUrl(properties.getProperty("jdbc.url"));
        druidDataSource.setUsername(properties.getProperty("jdbc.username"));
        druidDataSource.setPassword(properties.getProperty("jdbc.pwd"));

        druidDataSource.setInitialSize(Integer.parseInt(properties.getProperty("jdbc.initSize")));
        druidDataSource.setMaxActive(Integer.parseInt(properties.getProperty("jdbc.maxActive")));
        druidDataSource.setMaxWait(Integer.parseInt(properties.getProperty("jdbc.maxWait")));

        for (int i = 0; i < 10; i++) {
            Connection connection = druidDataSource.getConnection();
            System.out.println(i + "-------->" + connection);
        }
    }

    public static void useDruidByPropertiesFile2() throws Exception {
        Properties properties = new Properties();

        // 有bug，resourceAsStream一直为null
        InputStream resourceAsStream = DruidDemo.class.getResourceAsStream("jdbc2.properties");
        properties.load(resourceAsStream);

//        通过工厂模式搭配预定义好的配置名直接创建数据源
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        for (int i = 0; i < 10; i++) {
            Connection connection = dataSource.getConnection();
            System.out.println(i + "-------->" + connection);
        }
    }
}
