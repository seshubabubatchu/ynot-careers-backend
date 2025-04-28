package com.ynot.careers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ynot.careers.model.JobModel;

public interface JobRepository extends JpaRepository<JobModel, Long> {

    boolean existsByTitle(String title);

    @Query("SELECT j FROM JobModel j WHERE " +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
            "LOWER(j.metaDescription) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
            "LOWER(j.location) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
            "LOWER(j.jobType) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
            "LOWER(j.jobId) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
            "LOWER(j.category.categoryName) LIKE LOWER(CONCAT('%', :searchString, '%'))")
    List<JobModel> searchJobs(@Param("searchString") String searchString);

    JobModel findByTitle(String title);

}
