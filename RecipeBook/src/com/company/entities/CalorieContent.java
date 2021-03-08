package com.company.entities;

public class CalorieContent {

    private int id ;
    private int calories;
    private int proteins;
    private int fats;
    private int carbohydrates;


    public CalorieContent() {
    }

    public CalorieContent(int id, int calories, int proteins, int fats, int carbohydrates) {
        this.id = id;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public CalorieContent (int calories, int proteins, int fats, int carbohydrates) {
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public int getId() {
        return id;
    }

    public int getCalories() {
        return calories;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public int getFats() {
        return fats;
    }

    public int getProteins() {
        return proteins;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }


    @Override
    public String toString() {
        return "CalorieContent{" +
                "id=" + id +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                '}';
    }
}
