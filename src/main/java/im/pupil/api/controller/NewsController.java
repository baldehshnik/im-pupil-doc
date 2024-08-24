package im.pupil.api.controller;

import im.pupil.api.dto.NewsDto;
import im.pupil.api.exception.news.NewsNotFoundException;
import im.pupil.api.exception.news.response.NewsErrorResponse;
import im.pupil.api.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/education/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/findAll")
    public List<NewsDto> findAll() {
        return newsService
                .findAll()
                .stream()
                .map(newsService::convertToDto)
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<NewsErrorResponse> handleNotFoundException(NewsNotFoundException exception) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(exception.getMessage());

        return new ResponseEntity<>(newsErrorResponse, HttpStatus.NOT_FOUND);
    }
}
