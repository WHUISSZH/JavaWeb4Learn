package com.zh.fruit.dao;


import com.zh.fruit.prjo.Fruit;

import java.util.List;

public interface FruitDAO {
    List<Fruit> getFruitList();

    Fruit getFruitByID(int fid);

    int updateFruit(Fruit fruit);

    int delFruit(int fid);

    int addFruit(Fruit fruit);

    List<Fruit> getFruitListByPageNo(String keyword, int pageNO, int pageCapcity);

    List<Fruit> getFruitListByPageNo2(int pageNO, int pageCapcity);

    int getFruitCount();

    int getFruitCount4Search(String keyword);
}
