package com.ynot.careers.controller;

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
import com.ynot.careers.model.UserModel;
import com.ynot.careers.response.ApiResponse;
import com.ynot.careers.service.job.IJobService;
import com.ynot.careers.service.user.IUserService;
import static org.springframework.http.HttpStatus.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users/")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IJobService jobService;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<UserModel> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse("all users", allUsers));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching jobs", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<ApiResponse> findUserById(@PathVariable Long id) {
        try {
            UserModel user = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse("user", user));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching jobs", INTERNAL_SERVER_ERROR));
        }

    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserModel user) {
        try {
            UserModel newUSer = userService.addUser(user);
            return ResponseEntity.ok(new ApiResponse("user added", newUSer));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching jobs", INTERNAL_SERVER_ERROR));

        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        try {
            UserModel newUSer = userService.updateUser(user, id);
            return ResponseEntity.ok(new ApiResponse("user updated", newUSer));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching jobs", INTERNAL_SERVER_ERROR));

        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("user deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error fetching jobs", INTERNAL_SERVER_ERROR));

        }
    }

    @PutMapping("/user/{user_id}/save-job/{job_id}")
    public ResponseEntity<ApiResponse> addSavedJob(@PathVariable Long user_id, @PathVariable Long job_id) {
        try {
            JobModel jobToSave = jobService.getJobById(job_id);
            UserModel user = userService.getUserById(user_id);

            // Check if the job is already saved
            if (user.getSavedJobs() != null && user.getSavedJobs().contains(jobToSave)) {
                return ResponseEntity.ok(new ApiResponse("Job already saved", null));
            }

            // Save the job
            UserModel updatedUser = userService.saveJob(jobToSave, user_id);
            return ResponseEntity.ok(new ApiResponse("Saved the job", updatedUser));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error adding savedjob", INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/user/{user_id}/remove-saved-job/{job_id}")
    public ResponseEntity<ApiResponse> removeSavedJob(@PathVariable Long user_id, @PathVariable Long job_id) {
        try {
            JobModel jobToRemove = jobService.getJobById(job_id);
            UserModel user = userService.getUserById(user_id);

            // Check if the job is not saved
            if (user.getSavedJobs() == null || !user.getSavedJobs().contains(jobToRemove)) {
                return ResponseEntity.ok(new ApiResponse("Job not found in saved jobs", user));
            }

            // Remove the job
            UserModel updatedUser = userService.removeSavedJob(jobToRemove, user_id);
            return ResponseEntity.ok(new ApiResponse("Removed the job from saved jobs", updatedUser));

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error adding savedjob", INTERNAL_SERVER_ERROR));
        }
    }

}
