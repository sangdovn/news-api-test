package com.svngdo.news.newsapi;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.SourcesRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.models.response.SourcesResponse;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NewsApi {

    @Value("${newsapi.apikey}")
    private String apiKey;

    private NewsApiClient newsApiClient;

    @PostConstruct
    public void init() {
        newsApiClient = new NewsApiClient(apiKey);
    }

    public CompletableFuture<ArticleResponse> getEverything(String query) {
        CompletableFuture<ArticleResponse> future = new CompletableFuture<>();

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(query != null ? query : "apple")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        future.complete(response);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        future.completeExceptionally(throwable);
                    }
                }
        );
        return future;
    }

    public void getTopHeadlines(String query, String language) {
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .q(query != null ? query : "bitcoin")
                        .language(language != null ? language : "en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        System.out.println(response.getArticles().get(0).getTitle());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
    }

    public void getSource(String language, String country) {
        newsApiClient.getSources(
                new SourcesRequest.Builder()
                        .language(language != null ? language : "en")
                        .country(country != null ? country : "us")
                        .build(),
                new NewsApiClient.SourcesCallback() {
                    @Override
                    public void onSuccess(SourcesResponse response) {
                        System.out.println(response.getSources().get(0).getName());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                });
    }
}
