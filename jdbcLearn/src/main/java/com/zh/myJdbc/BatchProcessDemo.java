package com.zh.myJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/13 19:05
 * @description：
 * @modified By：
 * @version:
 */
public class BatchProcessDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        //批处理操作一： 如果要执行批处理任务，URL中需要添加一个参数：rewriteBatchedStatements=true
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fruitdb?serverTimezone=Asia/Shanghai&useSSL=false&rewriteBatchedStatements=true", "root", "zhanghui");
//        String sql = "insert into t_fruit values(0, ?, ?, ?, ?)";
        String sql = "delete from t_fruit where fid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 1; i <= 55; i++) {
//            preparedStatement.setString(1, "草莓" + i);
//            preparedStatement.setInt(2, i * 10);
//            preparedStatement.setInt(3, i * 30);
//            preparedStatement.setString(4, "过敏");

            preparedStatement.setInt(1, i + 38);

            //批处理操作二：把待处理状态对象添加到batch队列中
            preparedStatement.addBatch();

            //如果任务较多，可以分批次执行，每次执行完，清空任务队列
            if (i % 10000 == 0){
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        }
//        再执行一次批处理（如果分批次执行时，最后一批数量不足10000，那么这批数据会在本次处理。
//        用于成批地执行SQL语句，但不能执行返回值是ResultSet结果集的SQL语句
//      返回：1.数据库中受命令影响的行数执行
//      2. SUCCESS_NO_INFO ( -2)的值,表示命令为处理成功，但受影响的行数为未知
//      3. 如果批量更新中的命令之一无法正确执行，此方法引发BatchUpdateException，JDBC driver可能会也可能不会继续处理剩余的命令。
//          但是driver的行为是与特定的DBMS绑定的，要么总是继续处理命令，要么从不继续处理命令。如果驱动程序继续处理，方法将返回 EXECUTE_FAILED(-3)

        int[] iss = preparedStatement.executeBatch();
        for (int is : iss) {
            System.out.println(is);
        }

        preparedStatement.close();
        connection.close();
    }
}
