package com.zh.fruit.service.impl;

import com.zh.fruit.dao.FruitDAO;
import com.zh.fruit.prjo.Fruit;
import com.zh.fruit.service.FruitService;

import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/20 16:10
 * @description：
 * @modified By：
 * @version:
 */
public class FruitServiceImpl implements FruitService {
    FruitDAO fruitDAO = null;

    @Override
    public List<Fruit> getFruitList() {
        return fruitDAO.getFruitList();
    }

    @Override
    public Fruit getFruitByID(int fid) {
        return fruitDAO.getFruitByID(fid);
    }

    @Override
    public int delFruit(int fid) {
        return fruitDAO.delFruit(fid);
    }

    @Override
    public int updateFruit(Fruit fruit) {
        return fruitDAO.updateFruit(fruit);
    }

    @Override
    public int addFruit(Fruit fruit) {
        return fruitDAO.addFruit(fruit);
    }

    @Override
    public List<Fruit> getFruitListByPageNo(String keyword, int pageNO, int pageCapcity) {
        return fruitDAO.getFruitListByPageNo(keyword, pageNO, pageCapcity);
    }

    @Override
    public List<Fruit> getFruitListByPageNo2(int pageNO, int pageCapcity) {
        return fruitDAO.getFruitListByPageNo2(pageNO, pageCapcity);
    }

    @Override
    public int getFruitCount() {
        return fruitDAO.getFruitCount();
    }

    @Override
    public int getFruitCount4Search(String keyword) {
        return fruitDAO.getFruitCount4Search(keyword);
    }
}
