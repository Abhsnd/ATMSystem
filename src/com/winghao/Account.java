package com.winghao;

public class Account {
    private String cardId;          // 卡号
    private String username;        // 用户名
    private String password;        // 密码
    private double money;           // 余额
    private double quotaMoney;      // 单次取现额度

    public Account() {
    }

    public Account(String cardId, String username, String password, double quotaMoney) {
        this.cardId = cardId;
        this.username = username;
        this.password = password;
        this.quotaMoney = quotaMoney;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }
}
