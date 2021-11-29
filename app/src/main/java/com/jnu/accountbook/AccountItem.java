package com.jnu.accountbook;

public class AccountItem {
    private final String name;
    private final int pictureId;
    private final double money;

    public AccountItem(String name, int pictureId, double money) {
        this.name=name;
        this.pictureId = pictureId;
        this.money=money;
    }

    public String getName() {
        return name;
    }

    public int getPictureId() {
        return pictureId;
    }

    public double getMoney() {
        return money;
    }
}
