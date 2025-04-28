package com.ynot.careers.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ynot.careers.model.JobModel;
import com.ynot.careers.model.UserModel;
import com.ynot.careers.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new Error("cannot find user with given id"));
    }

    @Override
    public UserModel addUser(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public UserModel updateUser(UserModel user, Long id) {
        return Optional.ofNullable(getUserById(id)).map((oldUser) -> {
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setEmail(user.getEmail());
            oldUser.setPassword(user.getPassword());
            oldUser.setApplications(user.getApplications());
            oldUser.setSavedJobs(user.getSavedJobs());
            return userRepository.save(oldUser);
        }).orElseThrow(() -> new Error("cannot find user"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserModel saveJob(JobModel job, Long userId) {
        return Optional.ofNullable(getUserById(userId)).map(oldUser -> {
            List<JobModel> oldUser_SavedJobs = oldUser.getSavedJobs();
            oldUser_SavedJobs.add(job);
            return userRepository.save(oldUser);
        }).orElseThrow(() -> new Error("user not exist"));
    }

    @Override
    public UserModel removeSavedJob(JobModel job, Long userId) {
        return Optional.ofNullable(getUserById(userId)).map(oldUser -> {
            List<JobModel> oldUser_SavedJobs = oldUser.getSavedJobs();
            oldUser_SavedJobs.remove(job);
            return userRepository.save(oldUser);
        }).orElseThrow(() -> new Error("user not exist"));
    }

}
