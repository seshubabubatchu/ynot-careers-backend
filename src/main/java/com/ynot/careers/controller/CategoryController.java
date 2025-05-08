package com.ynot.careers.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ynot.careers.model.CategoryModel;
import com.ynot.careers.response.ApiResponse;
import com.ynot.careers.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories/")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<CategoryModel> allCategories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("all categories", allCategories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching categories", e));
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            CategoryModel category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("category found", category));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching category", e));
        }
    }

    @GetMapping("name/{name}")
    public ResponseEntity<ApiResponse> getCategoriesByName(@PathVariable String name) {
        try {
            CategoryModel categoryByGivenName = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("found categories with given name", categoryByGivenName));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching categories", e));
        }
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryModel category) {
        try {

            CategoryModel categoryExists = categoryService.getCategoryByName(category.getCategoryName());
            if (categoryExists != null) {
                return ResponseEntity.ok(new ApiResponse("category already exists", null));

            } else {
                CategoryModel newCategory = categoryService.addCategory(category);
                return ResponseEntity.ok(new ApiResponse("category added", newCategory));
            }

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error adding category", e));
        }

    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryModel category, @PathVariable Long id) {
        try {
            CategoryModel updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("category updated", updatedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error updating category", e));
        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            String deleteCategory = categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("deleted", deleteCategory));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error deleting category", e));
        }

    }

}
