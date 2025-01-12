package im.pupil.api.controller;

import im.pupil.api.dto.news.GetNewsInfoDto;
import im.pupil.api.dto.news.GetNewsListDto;
import im.pupil.api.exception.news.NewsNotFoundException;
import im.pupil.api.exception.news.response.NewsErrorResponse;
import im.pupil.api.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<GetNewsListDto> findAllNews() {
        return newsService.findAll();
    }

    @GetMapping("/{id}")
    public GetNewsInfoDto findNewsById(
            @PathVariable Integer id
    ) {
        return newsService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<NewsErrorResponse> handleNotFoundException(NewsNotFoundException exception) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(exception.getMessage());

        return new ResponseEntity<>(newsErrorResponse, HttpStatus.NOT_FOUND);
    }
}
