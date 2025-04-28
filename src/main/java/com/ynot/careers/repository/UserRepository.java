package com.ynot.careers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ynot.careers.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {

}
