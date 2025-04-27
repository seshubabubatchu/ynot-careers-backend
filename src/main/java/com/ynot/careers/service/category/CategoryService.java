package com.ynot.careers.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ynot.careers.model.CategoryModel;
import com.ynot.careers.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryModel> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new Error("Cannot get all categories", e);
        }
    }

    @Override
    public CategoryModel getCategoryById(Long id) {
        try {
            return categoryRepository.findById(id).orElseThrow(() -> new Error("No category found with given id"));
        } catch (Exception e) {
            throw new Error("Cannot get a category", e);
        }
    }

    @Override
    public List<CategoryModel> getCategoriesByName(String name) {
        try {
            return categoryRepository.findAllByCategoryName(name);
        } catch (Exception e) {
            throw new Error("Cannot get with name", e);

        }
    }

    @Override
    public CategoryModel addCategory(CategoryModel category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new Error("Cannot get with name", e);

        }
    }

    @Override
    public CategoryModel updateCategory(CategoryModel category, Long id) {
        try {
            return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
                oldCategory.setCategoryName(category.getCategoryName());
                return categoryRepository.save(oldCategory);
            }).orElseThrow(() -> new Error("No category found with given id"));
        } catch (Exception e) {
            throw new Error("Cannot update category", e);

        }
    }

    @Override
    public String deleteCategory(Long id) {
        try {
            return Optional.ofNullable(getCategoryById(id)).map(category -> {
                categoryRepository.deleteById(id);
                return "deleted";
            }).orElseThrow(() -> new Error("Cannot find category with given id"));

        } catch (Exception e) {
            throw new Error("Cannot delete category", e);
        }
    }

}
