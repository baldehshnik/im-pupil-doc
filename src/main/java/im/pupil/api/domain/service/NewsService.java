package im.pupil.api.domain.service;

import im.pupil.api.domain.dto.news.GetGuideDto;
import im.pupil.api.domain.dto.news.GetNewsInfoDto;
import im.pupil.api.domain.dto.news.GetNewsListDto;
import im.pupil.api.domain.exception.news.NewsNotFoundException;
import im.pupil.api.data.entity.news.Guide;
import im.pupil.api.data.entity.news.News;
import im.pupil.api.data.repository.GuideRepository;
import im.pupil.api.data.repository.NewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final GuideRepository guideRepository;

    private final ModelMapper modelMapper;

    public NewsService(
            NewsRepository newsRepository,
            GuideRepository guideRepository,
            ModelMapper modelMapper
    ) {
        this.newsRepository = newsRepository;
        this.guideRepository = guideRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<GetNewsListDto> findAll() {
        List<News> result = newsRepository.findAll();
        return result.stream()
                .map(this::convertToNewsListDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GetNewsInfoDto findById(Integer id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isEmpty()) throw new NewsNotFoundException();

        List<GetGuideDto> guides = guideRepository.findByNewsId(optionalNews.get().getId()).stream()
                .map(this::convertToGuideDto)
                .toList();

        GetNewsInfoDto getNewsInfoDto = convertToNewsInfoDto(optionalNews.get());
        getNewsInfoDto.setGuides(guides);

        return getNewsInfoDto;
    }

    private GetNewsListDto convertToNewsListDto(News news) {
        return modelMapper.map(news, GetNewsListDto.class);
    }

    private GetNewsInfoDto convertToNewsInfoDto(News news) {
        return modelMapper.map(news, GetNewsInfoDto.class);
    }

    private GetGuideDto convertToGuideDto(Guide guide) {
        return modelMapper.map(guide, GetGuideDto.class);
    }
}














