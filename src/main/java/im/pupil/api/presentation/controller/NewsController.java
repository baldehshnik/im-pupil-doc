package im.pupil.api.presentation.controller;

import im.pupil.api.domain.dto.news.GetNewsInfoDto;
import im.pupil.api.domain.dto.news.GetNewsListDto;
import im.pupil.api.domain.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<GetNewsListDto> findAllNews() {
        return newsService.findAll();
    }

    @GetMapping("/{id}")
    public GetNewsInfoDto findNewsById(@PathVariable Integer id) {
        return newsService.findById(id);
    }
}
















