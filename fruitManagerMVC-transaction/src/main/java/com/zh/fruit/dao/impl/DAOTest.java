package com.zh.fruit.dao.impl;

import com.zh.fruit.prjo.Fruit;

import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/15 20:20
 * @description：
 * @modified By：
 * @version:
 */
public class DAOTest {
    public static void main(String[] args) {
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList();
        fruitList.forEach(System.out::println);
    }
}
