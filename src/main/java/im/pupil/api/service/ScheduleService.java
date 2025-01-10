package im.pupil.api.service;

import im.pupil.api.dto.schedule.CreateNewScheduleDto;
import im.pupil.api.dto.schedule.GetScheduleDto;
import im.pupil.api.dto.schedule.UpdateScheduleDto;
import im.pupil.api.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.exception.schedule.ScheduleNotFoundException;
import im.pupil.api.model.Schedule;
import im.pupil.api.model.group.InstitutionGroup;
import im.pupil.api.repository.InstitutionGroupRepository;
import im.pupil.api.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final InstitutionGroupRepository institutionGroupRepository;

    private final LessonService lessonService;

    private final ModelMapper modelMapper;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            InstitutionGroupRepository institutionGroupRepository,
            LessonService lessonService,
            ModelMapper modelMapper
    ) {
        this.scheduleRepository = scheduleRepository;
        this.institutionGroupRepository = institutionGroupRepository;
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<GetScheduleDto> readSchedulesByGroupId(
        Integer groupId
    ) {
        List<Schedule> schedules = scheduleRepository.readSchedulesByGroupId(groupId);
        return schedules.stream()
                .map(m -> modelMapper.map(m, GetScheduleDto.class))
                .toList();
    }

    @Transactional
    public void createSchedule(
        CreateNewScheduleDto createNewScheduleDto
    ) {
        Optional<InstitutionGroup> institutionGroup = institutionGroupRepository.findById(createNewScheduleDto.getGroupId());
        if (institutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();

        Schedule schedule = modelMapper.map(createNewScheduleDto, Schedule.class);
        schedule.setType(0);
        schedule.setGroup(institutionGroup.get());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        createNewScheduleDto.getLessons().forEach(m ->
                lessonService.createALesson(savedSchedule.getId(), m)
        );
    }

    @Transactional
    public void updateSchedule(
            UpdateScheduleDto updateScheduleDto
    ) {
        Optional<InstitutionGroup> institutionGroup = institutionGroupRepository.findById(updateScheduleDto.getGroupId());
        if (institutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();

        Schedule schedule = modelMapper.map(updateScheduleDto, Schedule.class);
        schedule.setGroup(institutionGroup.get());
        scheduleRepository.save(schedule);
        lessonService.updateLessons(schedule.getId(), updateScheduleDto.getLessons());
    }

    @Transactional
    public void makeScheduleAsACurrent(
        Integer scheduleId
    ) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isEmpty()) throw new ScheduleNotFoundException();

        Schedule schedule = optionalSchedule.get();
        List<Schedule> schedules = scheduleRepository.readSchedulesByGroupId(schedule.getGroup().getId());
        schedules.remove(schedule);

        schedules.forEach(m -> {
            if (m.getType() == 1) {
                m.setType(0);
                scheduleRepository.save(m);
            }
        });

        schedule.setType(1);
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void clearScheduleStatus(
            Integer scheduleId
    ) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isEmpty()) throw new ScheduleNotFoundException();

        Schedule schedule = optionalSchedule.get();
        schedule.setType(0);
        scheduleRepository.save(schedule);
    }
}






















