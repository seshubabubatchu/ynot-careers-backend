package com.ynot.careers.model;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String metaDescription;
    private String description;
    private LocalDate postedDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("jobs")
    @JoinColumn(name = "categoryId")
    private CategoryModel category;

    private ArrayList<String> skills;
    private String jobId;
    private String location;
    private String jobType;
}
