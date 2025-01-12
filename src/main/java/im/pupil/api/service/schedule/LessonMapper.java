package im.pupil.api.service.schedule;

import im.pupil.api.dto.lesson.GetLessonDto;
import im.pupil.api.model.schedule.Lesson;
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






















