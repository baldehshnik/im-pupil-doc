package im.pupil.api.service.schedule;

import im.pupil.api.dto.lesson.GetLessonDto;
import im.pupil.api.dto.lesson.GetLessonWithPassStatusDto;
import im.pupil.api.dto.lesson.GetPassDto;
import im.pupil.api.dto.schedule.CreateNewScheduleDto;
import im.pupil.api.dto.schedule.GetScheduleDto;
import im.pupil.api.dto.schedule.UpdateScheduleDto;
import im.pupil.api.exception.institution_group.GroupMemberNotFoundException;
import im.pupil.api.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.exception.schedule.ScheduleNotFoundException;
import im.pupil.api.model.group.GroupMember;
import im.pupil.api.model.group.InstitutionGroup;
import im.pupil.api.model.schedule.Lesson;
import im.pupil.api.model.schedule.Pass;
import im.pupil.api.model.schedule.Schedule;
import im.pupil.api.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final InstitutionGroupRepository institutionGroupRepository;
    private final LessonRepository lessonRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final PassRepository passRepository;

    private final LessonService lessonService;

    private final ModelMapper modelMapper;
    private final LessonMapper lessonMapper;

    private final WeekTypeWorker weekTypeWorker;
    private final ScheduleValidator scheduleValidator;
    private final LocalDateConverter localDateConverter;
    private final LocalTimeConverter localTimeConverter;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            InstitutionGroupRepository institutionGroupRepository,
            LessonRepository lessonRepository,
            GroupMemberRepository groupMemberRepository,
            PassRepository passRepository,
            LessonService lessonService,
            ModelMapper modelMapper,
            LessonMapper lessonMapper,
            WeekTypeWorker weekTypeWorker,
            ScheduleValidator scheduleValidator,
            LocalDateConverter localDateConverter,
            LocalTimeConverter localTimeConverter
    ) {
        this.scheduleRepository = scheduleRepository;
        this.institutionGroupRepository = institutionGroupRepository;
        this.lessonRepository = lessonRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.passRepository = passRepository;
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
        this.lessonMapper = lessonMapper;
        this.weekTypeWorker = weekTypeWorker;
        this.scheduleValidator = scheduleValidator;
        this.localDateConverter = localDateConverter;
        this.localTimeConverter = localTimeConverter;
    }

    @Transactional(readOnly = true)
    public List<GetLessonWithPassStatusDto> readLessonsWithPassStatus(
            Integer groupMemberId,
            LocalDate date
    ) {
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(groupMemberId);
        if (optionalGroupMember.isEmpty()) throw new GroupMemberNotFoundException();

        List<GetLessonDto> lessons = readCurrentSchedule(
                optionalGroupMember.get().getGroup().getId(),
                date
        );
        List<GetLessonWithPassStatusDto> passStatusDtos = new ArrayList<>(lessons.size());
        for (GetLessonDto getLessonDto : lessons) {
            Instant instant = localTimeConverter.convertLocalTimeToInstant(getLessonDto.getStart(), date);
            Optional<Pass> pass = passRepository.readPass(
                    instant, getLessonDto.getId(), optionalGroupMember.get().getId()
            );

            if (pass.isEmpty()) {
                passStatusDtos.add(
                        new GetLessonWithPassStatusDto(
                                getLessonDto,
                                new GetPassDto(null, instant, -1)
                        )
                );
            } else {
                passStatusDtos.add(
                        new GetLessonWithPassStatusDto(
                                getLessonDto,
                                modelMapper.map(pass.get(), GetPassDto.class)
                        )
                );
            }
        }

        return passStatusDtos;
    }

    @Transactional(readOnly = true)
    public List<GetLessonDto> readCurrentSchedule(Integer groupId, LocalDate currentDate) {
        Schedule currentSchedule = findCurrentSchedule(groupId);
        scheduleValidator.validateSchedule(currentSchedule, currentDate);

        List<Lesson> allLessons = lessonRepository.findAllLessonsByScheduleId(currentSchedule.getId());
        if (allLessons.isEmpty()) return new ArrayList<>();

        Boolean isUpperWeek = determineUpperWeek(allLessons, currentSchedule, currentDate);
        return lessonRepository.findCurrentLessons(
                        currentSchedule.getId(),
                        weekTypeWorker.convertToInteger(isUpperWeek),
                        LocalDate.now().getDayOfWeek().getValue()
                ).stream()
                .map(lessonMapper::mapToDto)
                .toList();
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

    private Schedule findCurrentSchedule(Integer groupId) {
        return scheduleRepository.findCurrentScheduleByGroupId(groupId)
                .orElseThrow(ScheduleNotFoundException::new);
    }

    private Boolean determineUpperWeek(List<Lesson> allLessons, Schedule currentSchedule, LocalDate currentDate) {
        Lesson checkLesson = allLessons.get(0);
        boolean hasDifferenceWeekType = allLessons.stream()
                .anyMatch(lesson -> !checkLesson.getWeekType().equals(lesson.getWeekType()));

        Integer startType = currentSchedule.getStartType();
        LocalDate startDate = localDateConverter.convertInstantToLocalDate(currentSchedule.getStartDate());

        if (hasDifferenceWeekType) {
            return weekTypeWorker.isUpperWeek(startType, startDate, currentDate);
        } else {
            return weekTypeWorker.convertToBoolean(checkLesson.getWeekType());
        }
    }
}






















