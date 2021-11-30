package com.jnu.accountbook.data;

import java.io.Serializable;

public class AccountItem implements Serializable {
    private String name;
    private int pictureId;
    private double money;

    public AccountItem(String name, int pictureId, double money) {
        this.name=name;
        this.pictureId = pictureId;
        this.money=money;
    }

    public String getName() { return name; }

    public int getPictureId() { return pictureId; }

    public double getMoney() { return money; }

    public void setName(String name) { this.name = name; }

    public void setMoney(double money) { this.money = money; }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
