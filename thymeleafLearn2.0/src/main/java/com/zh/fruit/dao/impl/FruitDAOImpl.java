package com.zh.fruit.dao.impl;

import com.zh.fruit.dao.FruitDAO;
import com.zh.fruit.prjo.Fruit;
import com.zh.mySSM.baseDAO.BaseDAO;

import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/15 16:18
 * @description：
 * @modified By：
 * @version:
 */
public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit");
    }

    @Override
    public Fruit getFruitByID(int fid) {
        return super.querySingleObj("select * from t_fruit where fid = ?", fid);
    }

    @Override
    public int updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ?, price = ?, count = ?, remark = ? where fid = ?";
        return super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getCount(), fruit.getRemark(), fruit.getFid());
    }

    @Override
    public int delFruit(int fid) {
        return super.executeUpdate("delete from t_fruit where fid = ?", fid);
    }

    @Override
    public int addFruit(Fruit fruit) {
        return super.executeUpdate("insert into t_fruit values(0,?,?,?,?)", fruit.getFname(), fruit.getPrice(), fruit.getCount(), fruit.getRemark());
    }

    @Override
    public List<Fruit> getFruitListByPageNo(String keyword, int pageNo, int pageCapcity) {
        return super.executeQuery("select * from t_fruit where fname like ? or remark like ? limit ? , ?", "%" + keyword + "%", "%" + keyword + "%", (pageNo - 1) * pageCapcity, pageCapcity);
    }

    @Override
    public List<Fruit> getFruitListByPageNo2(int pageNo, int pageCapcity) {
        return super.executeQuery("select * from t_fruit limit ? , ?", (pageNo - 1) * pageCapcity, pageCapcity);
    }

    @Override
    public int getFruitCount() {
        return ((Long) super.executeComplexQuery("select count(*) from t_fruit")[0]).intValue();
    }

    @Override
    public int getFruitCount4Search(String keyword) {
        return ((Long) super.executeComplexQuery("select count(*) from t_fruit where fname like ? or remark like ?", "%" + keyword + "%", "%" + keyword + "%")[0]).intValue();
    }
}
