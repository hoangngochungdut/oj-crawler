package com.hung.project.models;

public class User {

    private int id;
    private String userName;

    private double dataStructureRate;
    private double algorithmsRate;
    private double usingAIRate;

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public User(int id, String userName,
                double dataStructureRate,
                double algorithmsRate,
                double usingAIRate) {
        this.id = id;
        this.userName = userName;
        this.dataStructureRate = dataStructureRate;
        this.algorithmsRate = algorithmsRate;
        this.usingAIRate = usingAIRate;
    }

    // ===== GETTERS =====
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public double getDataStructureRate() {
        return dataStructureRate;
    }

    public double getAlgorithmsRate() {
        return algorithmsRate;
    }

    public double getUsingAIRate() {
        return usingAIRate;
    }

    // ===== SETTERS =====
    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDataStructureRate(double dataStructureRate) {
        this.dataStructureRate = dataStructureRate;
    }

    public void setAlgorithmsRate(double algorithmsRate) {
        this.algorithmsRate = algorithmsRate;
    }

    public void setUsingAIRate(double usingAIRate) {
        this.usingAIRate = usingAIRate;
    }

    @Override
    public String toString() {
        return id + " - " + userName +
                " | DS: " + dataStructureRate +
                " | Algo: " + algorithmsRate +
                " | AI: " + usingAIRate;
    }
}