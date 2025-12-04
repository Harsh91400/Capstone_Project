package com.example.appservice.controller;

import com.example.appservice.model.AppDetails;
import com.example.appservice.repository.AppDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apps")
public class AppController {
    @Autowired
    private AppDetailsRepository appDetailsRepository;

    @PostMapping("/add")
    public AppDetails addApp(@RequestBody AppDetails app){
        return appDetailsRepository.save(app);
    }

    @GetMapping
    public List<AppDetails> getAll(){
        return appDetailsRepository.findAll();
    }

    @GetMapping("/genre/{genre}")
    public List<AppDetails> getByGenre(@PathVariable String genre){
        return appDetailsRepository.findByGenreIgnoreCase(genre);
    }
}
