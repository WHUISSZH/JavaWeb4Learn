package com.zh.mySSM.trans;

import com.zh.mySSM.baseDAO.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/21 15:09
 * @description：
 * @modified By：
 * @version:
 */
public class TransactionManager {
    public static void beginTrans() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
    }

    public static void commitTrans() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.commit();
        ConnUtil.closeConn();
    }

    public static void rollbackTrans() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.rollback();
        ConnUtil.closeConn();
    }
}
