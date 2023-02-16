package com.zh.fruit.dataAccseObject.impl;

import com.zh.fruit.dataAccseObject.FruitDAO;
import com.zh.fruit.proj.Fruit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/12 11:36
 * @description：
 * @modified By：
 * @version:
 */
public class FruitDAOImpl implements FruitDAO {
    final String DRIVER = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/fruitdb?serverTimezone=Asia/Shanghai&useSSL=false";
    final String USER = "root";
    final String PWD = "zhanghui";
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public List<Fruit> getFruitList() {
        ArrayList<Fruit> fruits = new ArrayList<Fruit>();

        try {
//            1. 加载驱动
            Class.forName(DRIVER);
//            2. 建立数据库连接
            connection = DriverManager.getConnection(URL, USER, PWD);
//            3. 编写sql
            String querySQL = "select * from t_fruit";
//            4. 创建预处理命令对象
            preparedStatement = connection.prepareStatement(querySQL);
//            5. 执行查询
            resultSet = preparedStatement.executeQuery();
//            6. 解析结果
            while (resultSet.next()) {
                int fid = resultSet.getInt(1);
                String fname = resultSet.getString(2);
                int price = resultSet.getInt(3);
                int count = resultSet.getInt(4);
                String remark = resultSet.getString(5);

                Fruit fruit = new Fruit(fid, fname, price, count, remark);
                fruits.add(fruit);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            7. finally语句用于释放资源
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return fruits;
    }

    public boolean addFruit(Fruit fruit) {
        boolean flag = false;
        try {
            Class.forName(DRIVER);

            connection = DriverManager.getConnection(URL, USER, PWD);

            String insertSQL = "insert into t_fruit values(0, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, fruit.getFname());
            preparedStatement.setInt(2, fruit.getPrice());
            preparedStatement.setInt(3, fruit.getCount());
            preparedStatement.setString(4, fruit.getRemark());

            int i = preparedStatement.executeUpdate();
            flag = i > 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public boolean updateFruit(Fruit fruit) {
        try {
            Class.forName(DRIVER);

            connection = DriverManager.getConnection(URL, USER, PWD);

            String updateSQL = "update t_fruit set fcount  = ? where fid = ?";
            preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setInt(1, fruit.getCount());
            preparedStatement.setInt(2, fruit.getFid());

            return preparedStatement.executeUpdate() > 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Fruit getFruitByFname(String fname) {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PWD);

            String querySQL = "select * from t_fruit where fname = ?";
            preparedStatement = connection.prepareStatement(querySQL);
            preparedStatement.setString(1, fname);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int fid = resultSet.getInt(1);
                int price = resultSet.getInt(3);
                int count = resultSet.getInt(4);
                String remark = resultSet.getString(5);

                return new Fruit(fid, fname, price, count, remark);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean delFruit(Fruit fruit) {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PWD);

            String delSQL = "delete from t_fruit where fname = ?";
            preparedStatement = connection.prepareStatement(delSQL);

            preparedStatement.setString(1, fruit.getFname());

            return preparedStatement.executeUpdate() > 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}