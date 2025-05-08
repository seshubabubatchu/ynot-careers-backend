package com.ynot.careers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ynot.careers.model.ApplicationModel;

public interface ApplicationRepository extends JpaRepository<ApplicationModel, Long> {

}
