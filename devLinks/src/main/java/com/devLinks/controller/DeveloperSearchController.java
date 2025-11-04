package com.devLinks.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devLinks.dto.DeveloperProfileResponse;
import com.devLinks.service.DeveloperProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/developers")
@RequiredArgsConstructor
public class DeveloperSearchController {
    private final DeveloperProfileService developerProfileService;

    @GetMapping("/search")
    public List<DeveloperProfileResponse> searchDeveloper(
        @RequestParam(required=false) String name,
        @RequestParam(required=false) String skill,
        @RequestParam(required=false) String location
    ){
        return developerProfileService.searchDeveloper(name, skill, location);
    }
}
