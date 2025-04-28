package com.ynot.careers.service.job;

import java.util.List;

import com.ynot.careers.model.JobModel;

public interface IJobService {
    List<JobModel> getAllJobs();

    JobModel getJobById(Long id);

    List<JobModel> getJobsBySearch(String name);

    JobModel findByTitle(String title);

    JobModel addJob(JobModel job);

    public JobModel updateJob(JobModel job, Long id);

    void deleteJob(Long id);
}
