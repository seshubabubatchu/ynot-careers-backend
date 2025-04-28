package com.ynot.careers.service.job;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ynot.careers.model.CategoryModel;
import com.ynot.careers.model.JobModel;
import com.ynot.careers.repository.CategoryRepository;
import com.ynot.careers.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService implements IJobService {

    private final JobRepository jobRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<JobModel> getAllJobs() {
        try {
            return jobRepository.findAll();
        } catch (Exception e) {
            throw new Error("Cannot get jobs", e);
        }
    }

    @Override
    public JobModel getJobById(Long id) {

        try {
            return jobRepository.findById(id).orElseThrow(() -> new Error("cannot find job with given id"));
        } catch (Exception e) {
            throw new Error("Cannot get job", e);

        }
    }

    @Override
    public List<JobModel> getJobsBySearch(String name) {
        try {
            return jobRepository.searchJobs(name);
        } catch (Exception e) {
            throw new Error("Cannot get job", e);
        }
    }

    @Override
    public JobModel addJob(JobModel job) {
        CategoryModel category = Optional
                .ofNullable(categoryRepository.findByCategoryName(job.getCategory().getCategoryName()))
                .orElseGet(() -> {
                    CategoryModel newCategory = new CategoryModel(job.getCategory().getCategoryName());
                    return categoryRepository.save(newCategory);
                });
        job.setCategory(category);
        return jobRepository.save(job);
    }

    @Override
    public JobModel updateJob(JobModel job, Long id) {
        CategoryModel category = Optional
                .ofNullable(categoryRepository.findByCategoryName(job.getCategory().getCategoryName()))
                .orElseGet(() -> {
                    CategoryModel newCategory = new CategoryModel(job.getCategory().getCategoryName());
                    return categoryRepository.save(newCategory);
                });
        job.setCategory(category);
        return Optional.ofNullable(getJobById(id)).map(oldjob -> {
            oldjob.setTitle(job.getTitle());
            oldjob.setMetaDescription(job.getMetaDescription());
            oldjob.setDescription(job.getDescription());
            oldjob.setSkills(job.getSkills());
            oldjob.setJobId(job.getJobId());
            oldjob.setLocation(job.getLocation());
            oldjob.setJobType(job.getJobType());
            oldjob.setCategory(category);
            return jobRepository.save(oldjob);
        }).orElseThrow(() -> new Error("No Job Found with mentioned id"));
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public JobModel findByTitle(String title) {
        try {

            return jobRepository.findByTitle(title);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

}
