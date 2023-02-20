package com.zh.fruit.service;

import com.zh.fruit.prjo.Fruit;

import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/20 16:01
 * @description：
 * @modified By：
 * @version:
 */
public interface FruitService {
    List<Fruit> getFruitList();

    Fruit getFruitByID(int fid);

    int delFruit(int fid);

    int updateFruit(Fruit fruit);

    int addFruit(Fruit fruit);

    List<Fruit> getFruitListByPageNo(String keyword, int pageNO, int pageCapcity);

    List<Fruit> getFruitListByPageNo2(int pageNO, int pageCapcity);

    int getFruitCount();

    int getFruitCount4Search(String keyword);
}
