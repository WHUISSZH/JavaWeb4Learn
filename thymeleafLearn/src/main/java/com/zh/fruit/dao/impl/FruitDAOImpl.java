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
}
