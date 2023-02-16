package com.zh.fruit.dataAccseObject;

import com.zh.fruit.proj.Fruit;

import java.util.List;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/12 11:28
 * @description：
 * @modified By：
 * @version:
 */
public interface FruitDAO {
    // 查询库存列表
    List<Fruit> getFruitList();

    // 新增库存
    boolean addFruit(Fruit fruit);

    // 修改库存
    boolean updateFruit(Fruit fruit);

    // 根据名称查询特定水果库存
    Fruit getFruitByFname(String fname);

    // 删除特定水果库存（水果下架）
    boolean delFruit(Fruit fruit);
}
