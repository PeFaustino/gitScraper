package com.backend.project.controller;

import com.backend.project.dto.FilesDto;
import com.backend.project.services.ScraperService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("github")
public class GithubController {

    private final ScraperService scraperService = new ScraperService();


    @GetMapping("{owner}/{repo}")
    public ResponseEntity<List<FilesDto>> get(@PathVariable String owner, @PathVariable String repo) {
        return ResponseEntity.ok(scraperService.getHtml("https://github.com/" + owner + "/" + repo, new ArrayList<>()));
    }

}
