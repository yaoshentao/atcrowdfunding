package com.atguigu.crowd.entity;

public class Subject {
    private String subName;
    private Integer subScore;

    public Subject(String subName, Integer subScore) {
        this.subName = subName;
        this.subScore = subScore;
    }

    public Subject() {
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subName='" + subName + '\'' +
                ", subScore=" + subScore +
                '}';
    }

    public Integer getSubScore() {
        return subScore;
    }

    public void setSubScore(Integer subScore) {
        this.subScore = subScore;
    }
}
