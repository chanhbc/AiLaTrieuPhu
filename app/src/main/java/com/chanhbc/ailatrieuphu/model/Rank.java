package com.chanhbc.ailatrieuphu.model;

public class Rank {
    private String name;
    private String question;
    private String money;

    public Rank(String name, String question, String money) {
        this.name = name;
        this.question = question;
        this.money = money;
    }

    public String getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public String getQuestion() {
        return question;
    }
}
