package com.company.entities;

import com.company.entities.CalorieContent;

public class Recipe  {
    private int id;
    private String title;
    private String ingredients;
    private String description;
    private CalorieContent calorieContent;
    private Category category;

    public Recipe() {
    }

    public Recipe (String title, String ingredients, String  description, CalorieContent calorieContent, Category category) {
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.calorieContent = calorieContent;
        this.category = category;

    }

    public Recipe(int id, String title, String ingredients, String description, CalorieContent calorieContent, Category category) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.calorieContent = calorieContent;
        this.category = category;
    }

    public CalorieContent getCalorieContent() {
        return calorieContent;
    }

    public Category getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCalorieContent(CalorieContent calorieContent) {
        this.calorieContent = calorieContent;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", description='" + description + '\'' +
                ", calorieContent=" + calorieContent +
                ", category=" + category +
                '}';
    }
}
