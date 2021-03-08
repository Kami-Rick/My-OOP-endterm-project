package com.company.repositories.interfaces;

import com.company.entities.Category;

import java.util.List;

public interface ICategoryRepository {
    public Category getCategory(int id);
    public Category getCategory (String name);
    public List<Category> allCategories();
}
