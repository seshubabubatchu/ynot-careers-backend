package com.ynot.careers.service.user;

import java.util.List;

import com.ynot.careers.model.JobModel;
import com.ynot.careers.model.UserModel;

public interface IUserService {
    List<UserModel> getAllUsers();

    UserModel getUserById(Long id);

    UserModel addUser(UserModel job);

    public UserModel updateUser(UserModel job, Long id);

    void deleteUser(Long id);

    UserModel saveJob(JobModel job, Long userId);

    UserModel removeSavedJob(JobModel job, Long userId);
}
