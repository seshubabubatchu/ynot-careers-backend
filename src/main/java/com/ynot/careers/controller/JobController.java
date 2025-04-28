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

import com.ynot.careers.model.JobModel;
import com.ynot.careers.repository.JobRepository;
import com.ynot.careers.response.ApiResponse;
import com.ynot.careers.service.job.IJobService;
import static org.springframework.http.HttpStatus.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/jobs")
public class JobController {

    private final IJobService jobService;
    private final JobRepository jobRepo;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> getAllJobs() {
        try {
            List<JobModel> alljobs = jobService.getAllJobs();
            return ResponseEntity.ok(new ApiResponse("allJobs", alljobs));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching jobs", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<ApiResponse> getJobById(@PathVariable Long id) {
        try {
            JobModel job = jobService.getJobById(id);
            return ResponseEntity.ok(new ApiResponse("found", job));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("no job found with the given id", NOT_FOUND));
        }
    }

    @GetMapping("title/{title}")
    public ResponseEntity<ApiResponse> getJobById(@PathVariable String title) {
        try {
            JobModel job = jobService.findByTitle(title);
            return ResponseEntity.ok(new ApiResponse("found", job));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("no job found with the given title", NOT_FOUND));
        }
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addJob(@RequestBody JobModel job) {
        try {
            if (jobRepo.existsByTitle(job.getTitle())) {
                return ResponseEntity.ok(new ApiResponse("job already exists with the same title", null));

            } else {
                JobModel new_job = jobService.addJob(job);
                return ResponseEntity.ok(new ApiResponse("added", new_job));
            }

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("cannot add job", e));
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateJob(@PathVariable Long id,
            @RequestBody JobModel job) {
        try {
            JobModel new_job = jobService.updateJob(job, id);
            return ResponseEntity.ok(new ApiResponse("updated", new_job));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error updating job", INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.ok(new ApiResponse("deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error deleting job", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("search/{searchString}")
    public ResponseEntity<ApiResponse> getJobsBySearch(@PathVariable String searchString) {
        try {
            List<JobModel> matchedJobs = jobService.getJobsBySearch(searchString);
            return ResponseEntity.ok(new ApiResponse("search found", matchedJobs));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error deleting job", INTERNAL_SERVER_ERROR));

        }
    }
}
