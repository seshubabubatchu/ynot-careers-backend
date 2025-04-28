package com.ynot.careers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ynot.careers.model.CategoryModel;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    List<CategoryModel> findAllByCategoryName(String name);

    CategoryModel findByCategoryName(String name);
}
