package com.ynot.careers.service.category;

import java.util.List;

import com.ynot.careers.model.CategoryModel;

public interface ICategoryService {
    List<CategoryModel> getAllCategories();

    CategoryModel getCategoryById(Long id);

    CategoryModel getCategoryByName(String name);

    CategoryModel addCategory(CategoryModel category);

    public CategoryModel updateCategory(CategoryModel category, Long id);

    String deleteCategory(Long id);
}
