package im.pupil.api.domain.service.schedule;

import im.pupil.api.domain.dto.lesson.GetLessonDto;
import im.pupil.api.data.entity.schedule.Lesson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    private final ModelMapper modelMapper;

    public LessonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GetLessonDto mapToDto(Lesson lesson) {
        return modelMapper.map(lesson, GetLessonDto.class);
    }
}






















