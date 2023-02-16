package com.zh.fruit.dataAccseObject.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/12 21:09
 * @description：fruitdb中可能有多张表，操作不同表有着不同的DAO类，可以把数据库公共信息抽取出来单独作为一个上层抽象类， 专门给特定DAO类去继承
 * @modified By：
 * @version:
 */
public abstract class BaseDAO<T> {
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/fruitdb?serverTimezone=Asia/Shanghai&useSSL=false";
    private final String USER = "root";
    private final String PWD = "zhanghui";

    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    //    T的Class对象
    private Class entityClass;

    public BaseDAO() {
//        Generic 泛型
//        getClass() 获取Class对象，当新建BaseDAO<T>的实现类FruitDAOImpl时（new FruitDAOImpl<Fruit>()），创建的是FruitDAOImpl的实例
//        那么子类的构造方法会首先调用父类的无参构造方法，此处的getClass（）方法会被执行，获取的是FruitDAOImpl的Class对象，
//        然后getGenericSuperclass()获取得到父类BaseDAO<Fruit>的泛型类别Fruit。
        Type genericType = getClass().getGenericSuperclass();
//        ParameterizedType 参数化类型
//        获取BaseDAO<T>中T的真实类型列表，因为这里只有1个泛型T，所以直接取actualTypeArguments[0]
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        Type actualType = actualTypeArguments[0];
        try {
//            类加载，得到T的Class对象
            entityClass = Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 连接对象
     */
    protected Connection getCon() {
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
     * 关闭资源
     */
    protected void close() {
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

    /**
     * 设置预备状态对象sql参数
     *
     * @param params sql参数
     * @throws SQLException
     */
    private void setParams(PreparedStatement pstm, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i + 1, params[i]);
            }
        }
    }

    /**
     * @param obj           待设置属性值的对象
     * @param property      属性名
     * @param propertyValue 属性值
     */
    private void setValue(Object obj, String property, Object propertyValue) {
        Class clazz = obj.getClass();
        try {
//        获取property字符串对应的属性名， 比如 fid 去找obj对象中的 fid  属性，
//        数据库中的列名要和实体类的属性名一致，数据类型也要一致
            Field field = clazz.getDeclaredField(property);
            if (field != null) {
                field.setAccessible(true);
                field.set(obj, propertyValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增 删 改 操作的一般方法，差别在方法的两个输入参数
     *
     * @param sql
     * @param params
     * @return
     */
    protected int executeUpdate(String sql, Object... params) {
        try {
            connection = getCon();
            preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }

    /**
     * 在executeUpdate（）基础上实现 插入记录时获取最新插入记录的自增id值
     *
     * @param sql
     * @param params
     * @return 插入记录时获取最新插入记录的自增id值, 删除和更新操作则返回影响行数
     */
    protected int executeUpdatePlus(String sql, Object... params) {
        boolean insertFlag = false;
        insertFlag = sql.trim().toUpperCase().startsWith("INSERT");
        try {
            connection = getCon();
            if (insertFlag) {
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = connection.prepareStatement(sql);
            }
            setParams(preparedStatement, params);
            int i = preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            如果不是插入操作，那么generatedKeys为空，其next()方法为false
            if (generatedKeys.next()) {
                return (int) generatedKeys.getLong(1);
            }
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }

    /**
     * 查询表中兴趣记录
     *
     * @param sql    查询语句
     * @param params 状态对象参数
     * @return 记录结果集
     */
    protected List<T> executeQuery(String sql, Object... params) {
        ArrayList<T> list = new ArrayList<>();
        try {
            connection = getCon();
            preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, params);

            ResultSet resultSet = preparedStatement.executeQuery();
//            作为查询的通用方法，我们不知道获取的rs的具体情况，所以需要利用rs携带的元数据来解析rs
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//            获取结果集的列数
            int columnCount = resultSetMetaData.getColumnCount();
//            解析rs
            while (resultSet.next()) {
                T entity = (T) entityClass.newInstance();

                for (int i = 0; i < columnCount; i++) {
//                    通过元数据获取列名
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);
                    setValue(entity, columnName, columnValue);
                }
                list.add(entity);
            }

        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    /**
     * 查询单行记录
     *
     * @param sql    查询语句
     * @param params 状态对象参数
     * @return 单个记录对应对象
     */
    protected T querySingleObj(String sql, Object... params) {
        try {
            connection = getCon();
            preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, params);

            ResultSet resultSet = preparedStatement.executeQuery();
//            作为查询的通用方法，我们不知道获取的rs的具体情况，所以需要利用rs携带的元数据来解析rs
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//            获取结果集的列数
            int columnCount = resultSetMetaData.getColumnCount();
//            解析rs
            if (resultSet.next()) {
                T entity = (T) entityClass.newInstance();

                for (int i = 0; i < columnCount; i++) {
//                    通过元数据获取列名
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);
                    setValue(entity, columnName, columnValue);
                }
                return entity;
            }

        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    /**
     * 执行复杂查询，返回例如统计结果，
     *
     * @param sql
     * @param params
     * @return 查询结果记录
     */
    protected Object[] executeComplexQuery(String sql, Object... params) {
        try {
            connection = getCon();
            preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, params);

            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            Object[] complexArr = new Object[columnCount];

            while (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    complexArr[i] = resultSet.getObject(i + 1);
                }
            }
            return complexArr;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }
}
