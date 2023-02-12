package com.zh.fruitManager.controller;

import com.zh.fruitManager.dataAccseObject.FruitDAO;
import com.zh.fruitManager.dataAccseObject.impl.FruitDAOImpl;
import com.zh.fruitManager.proj.Fruit;

import java.util.List;
import java.util.Scanner;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/10 19:04
 * @description：菜单类
 * @modified By：
 * @version:
 */
public class Menu {
    Scanner scanner = new Scanner(System.in);
//    父类引用指向子类对象
    FruitDAO fruitDAO = new FruitDAOImpl();

    public int showMainMenu(){
        System.out.println("====================欢迎使用水果库存管理系统====================");
        System.out.println("1. 查看库存列表");
        System.out.println("2. 添加库存信息");
        System.out.println("3. 查看特定水果库存信息");
        System.out.println("4. 水果下架");
        System.out.println("5. 退出");
        System.out.println("===========================================================");
        System.out.print("请选择：");

        int i = scanner.nextInt();
        return i;
    }

    public void showFruitList(){
        List<Fruit> fruitList = fruitDAO.getFruitList();
        System.out.println("==========================当前库存==============================");
        System.out.println("编号\t\t名称\t\t价格\t\t数量\t\t备注");
        if (fruitList == null || fruitList.size() <= 0){
            System.out.println("对不起，库存为空！！！");
        }
        fruitList.forEach(System.out::println);
        System.out.println("===============================================================");
    }

    /**
     * 业务方法
     * 添加水果
     * 1. 如果新添水果不在数据库中，那么直接添加
     * 2. 水果原来在数据库中，那么只修改水果的部分信息
     * @param
     * */
    public void addFruit(){
        System.out.print("请输入待添加水果名称：");
//        以回车为结束符，next()读取的内容默认去掉空白符号（包括输入字符串的中间）；
        String fname = scanner.next();
        Fruit fruitGot = fruitDAO.getFruitByFname(fname);

        if (fruitGot == null){
            System.out.print("请输入待添加水果价格：");
            int price = scanner.nextInt();
            System.out.print("请输入待添加水果数量：");
            int count = scanner.nextInt();
            System.out.print("请输入待添加水果备注：");
            String remark = scanner.next();

            Fruit fruit = new Fruit(0, fname, price, count, remark);
            boolean isAdd = fruitDAO.addFruit(fruit);
            if (isAdd) {
                System.out.println("添加成功");
            }else {
                System.out.println("添加失败");
            }
        }else {
            System.out.print("请输入追加的数量：");
            int addCount = scanner.nextInt();

            fruitGot.setCount(addCount  + fruitGot.getCount());

            boolean isUpdated = fruitDAO.updateFruit(fruitGot);
            if (isUpdated) {
                System.out.println("追加成功");
            }else {
                System.out.println("追加失败");
            }
        }
    }

//    查看特定水果库存信息
    public void showSpecialFruitInfo(){
        System.out.print("请输入查询水果名称：");
        String fname = scanner.next();

        Fruit fruitGot = fruitDAO.getFruitByFname(fname);

        if (fruitGot == null){
            System.out.println("查无此果");
        }else {
            System.out.println("编号\t\t名称\t\t价格\t\t数量\t\t备注");
            System.out.println(fruitGot);
        }
    }

    /**
     * 水果下架
     */
    public void delFruit(){
        System.out.print("请输入下架水果名称：");
        String fname = scanner.next();

        Fruit fruitGot = fruitDAO.getFruitByFname(fname);
        if (fruitGot == null){
            System.out.println("下架水果不存在");
        }else {
            System.out.print("请确认是否下架（Y/N）");
            String input = scanner.next();
            if ("y".equalsIgnoreCase(input)){
                boolean isDel = fruitDAO.delFruit(fruitGot);
                if (isDel){
                    System.out.println("下架成功");
                }else {
                    System.out.println("下架失败");
                }
            }
        }
    }


    public boolean exit() {
        System.out.print("请确认是否退出（Y/N）：");
        String i = scanner.next();
        return !"Y".equalsIgnoreCase(i);
    }
}
