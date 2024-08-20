package im.pupil.api.service;

import im.pupil.api.dto.NewsDto;
import im.pupil.api.model.News;
import im.pupil.api.repository.NewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NewsService {
    NewsRepository newsRepository;
    ModelMapper modelMapper;

    @Autowired
    public NewsService(NewsRepository newsRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public NewsDto convertToDto (News news) {
        return modelMapper.map(news, NewsDto.class);
    }

    public News convertToNews(NewsDto newsDto) {
        return modelMapper.map(newsDto, News.class);
    }
}
