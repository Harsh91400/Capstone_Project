package com.example.appservice.repository;

import com.example.appservice.model.AppDetails;   
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<AppDetails, Long> {
}
