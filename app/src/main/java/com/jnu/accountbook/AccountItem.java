package com.jnu.accountbook;

public class AccountItem {
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
}
