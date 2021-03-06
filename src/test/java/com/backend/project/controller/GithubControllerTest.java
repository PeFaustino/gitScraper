package com.backend.project.controller;

import com.backend.project.dto.FilesDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class GithubControllerTest {

    @InjectMocks
    private GithubController githubController;

    @Test
    public void returnSuccessEmptyList_() {
        ResponseEntity<List<FilesDto>> response = githubController.get("PeFaustino", "empty");

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void returnSuccessList_() {
        ResponseEntity<List<FilesDto>> response = githubController.get("PeFaustino", "gitScraper");

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).size()).isGreaterThan(0);
    }

}
