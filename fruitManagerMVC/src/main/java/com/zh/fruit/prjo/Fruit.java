package com.zh.fruit.prjo;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/15 16:19
 * @description：
 * @modified By：
 * @version:
 */
public class Fruit {
    private int fid;
    private String fname;
    private int price;
    private int count;
    private String remark;

    public Fruit() {
    }

    public Fruit(int fid, String fname, int price, int count, String remark) {
        this.fid = fid;
        this.fname = fname;
        this.price = price;
        this.count = count;
        this.remark = remark;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return fid + "\t\t" + fname + "\t\t" + price + "\t\t" + count + "\t\t" + remark;
    }
}
