package im.pupil.api.service;

import im.pupil.api.dto.lesson.AddLessonDto;
import im.pupil.api.dto.lesson.GetLessonDto;
import im.pupil.api.dto.lesson.UpdateLessonDto;
import im.pupil.api.exception.schedule.LessonNotFoundException;
import im.pupil.api.exception.schedule.ScheduleNotFoundException;
import im.pupil.api.model.Lesson;
import im.pupil.api.model.Schedule;
import im.pupil.api.repository.LessonRepository;
import im.pupil.api.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ScheduleRepository scheduleRepository;

    private final ModelMapper modelMapper;

    public LessonService(
            LessonRepository lessonRepository,
            ScheduleRepository scheduleRepository,
            ModelMapper modelMapper
    ) {
        this.lessonRepository = lessonRepository;
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public GetLessonDto readLesson(
        Integer id
    ) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (lesson.isEmpty()) throw new LessonNotFoundException();

        return modelMapper.map(lesson.get(), GetLessonDto.class);
    }

    @Transactional(readOnly = true)
    public List<GetLessonDto> readLessonsByScheduleId(
            Integer id
    ) {
        List<Lesson> lessons = lessonRepository.findAllLessonsByScheduleId(id);
        return lessons.stream()
                .map(m -> modelMapper.map(m, GetLessonDto.class))
                .toList();
    }

    @Transactional
    public void createALesson(
            Integer scheduleId,
            AddLessonDto addLessonDto
    ) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isEmpty()) throw new ScheduleNotFoundException();

        Lesson lesson = modelMapper.map(addLessonDto, Lesson.class);
        lesson.setSchedule(optionalSchedule.get());
        lessonRepository.save(lesson);
    }

    @Transactional
    public void updateLessons(
            Integer scheduleId,
            List<UpdateLessonDto> updateLessonDtos
    ) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isEmpty()) throw new ScheduleNotFoundException();

        List<Lesson> existLessons = lessonRepository.findAllLessonsByScheduleId(scheduleId);

        Set<Integer> lessonIds = updateLessonDtos.stream()
                .map(UpdateLessonDto::getId)
                .collect(Collectors.toSet());

        List<Lesson> lessonsToDelete = existLessons.stream()
                .filter(lesson -> !lessonIds.contains(lesson.getId()))
                .collect(Collectors.toList());

        lessonRepository.deleteAll(lessonsToDelete);

        updateLessonDtos.forEach(m -> {
            Lesson lesson = modelMapper.map(m, Lesson.class);
            lesson.setSchedule(optionalSchedule.get());
            lessonRepository.save(lesson);
        });
    }
}

































