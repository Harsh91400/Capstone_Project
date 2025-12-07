package com.example.appservice.repository;

import com.example.appservice.model.AppDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppDetailsRepository extends JpaRepository<AppDetails, Long> {

    List<AppDetails> findByGenreIgnoreCase(String genre);
    List<AppDetails> findByAppOwnerId(Long appOwnerId);
}
