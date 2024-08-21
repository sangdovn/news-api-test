package com.svngdo.news.controller;

import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.svngdo.news.newsapi.NewsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NewsController {

    private final NewsApi newsApi;

    @GetMapping("/news")
    public ResponseEntity<?> getEverything(@RequestParam(name = "q") String query) {
        CompletableFuture<ArticleResponse> future = newsApi.getEverything(query);
        return new ResponseEntity<>(future.join(), HttpStatus.OK);
    }
}
