package com.zh.fruit.view;

import com.zh.fruit.controller.Menu;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/10 19:07
 * @description：
 * @modified By：
 * @version:
 */
public class Client {
    public static void main(String[] args) {
        Menu menu = new Menu();
        boolean flag = true;

        while (flag){
            int i = menu.showMainMenu();
            System.out.println(">>>你的选择是：" + i);

            switch (i) {
                case 1:
                    menu.showFruitList();
                    break;
                case 2:
                    menu.addFruit();
                    menu.showFruitList();
                    break;
                case 3:
                    menu.showSpecialFruitInfo();
                    break;
                case 4:
                    menu.delFruit();
                    menu.showFruitList();
                    break;
                case 5:
                    flag = menu.exit();
                    break;
                default:
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!请正确输入！!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    break;
            }
        }
        System.out.println("谢谢使用！再见！");
    }
}
