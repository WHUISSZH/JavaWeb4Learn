package com.zh.fruitManager.dataAccseObject.impl;

import com.zh.fruitManager.dataAccseObject.FruitDAO;
import com.zh.fruitManager.dataAccseObject.base.BaseDAO;
import com.zh.fruitManager.proj.Fruit;

import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/12 11:36
 * @description：
 * @modified By：
 * @version:
 */
public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {

    public List<Fruit> getFruitList() {
        String sql = "select * from t_fruit";
        return super.executeQuery(sql);
    }

    public boolean addFruit(Fruit fruit) {
        String insertSQL = "insert into t_fruit values(0, ?, ?, ?, ?)";
        int i = super.executeUpdatePlus(insertSQL, fruit.getFname(), fruit.getPrice(), fruit.getCount(), fruit.getRemark());
        if (i > 0){ // 不大于0说明添加失败
            System.out.println("新增记录的自增id为"  + i);
        }
        return i > 0;
    }

    public boolean updateFruit(Fruit fruit) {
        String updateSQL = "update t_fruit set fcount  = ? where fid = ?";
        return super.executeUpdate(updateSQL, fruit.getCount(), fruit.getFid()) > 0;
    }

    public Fruit getFruitByFname(String fname) {
        String querySQL = "select * from t_fruit where fname = ?";
        return super.querySingleObj(querySQL, fname);
    }

    public boolean delFruit(Fruit fruit) {
        String delSQL = "delete from t_fruit where fname = ?";
        return super.executeUpdate(delSQL, fruit.getFname()) > 0;
    }
}