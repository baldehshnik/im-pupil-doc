package im.pupil.api.domain.service.schedule;

import im.pupil.api.data.entity.Pupil;
import im.pupil.api.data.entity.group.GroupMember;
import im.pupil.api.data.entity.group.InstitutionGroup;
import im.pupil.api.data.entity.schedule.Lesson;
import im.pupil.api.data.entity.schedule.Pass;
import im.pupil.api.data.entity.schedule.Schedule;
import im.pupil.api.data.repository.*;
import im.pupil.api.domain.dto.lesson.GetLessonDto;
import im.pupil.api.domain.dto.lesson.GetLessonWithPassStatusDto;
import im.pupil.api.domain.dto.lesson.GetPassDto;
import im.pupil.api.domain.dto.schedule.CreateNewScheduleDto;
import im.pupil.api.domain.dto.schedule.GetScheduleDto;
import im.pupil.api.domain.dto.schedule.GetScheduleWithLessonsDto;
import im.pupil.api.domain.dto.schedule.UpdateScheduleDto;
import im.pupil.api.domain.exception.UnexpectedException;
import im.pupil.api.domain.exception.admin.NotEnoughAccessException;
import im.pupil.api.domain.exception.exam.PupilAreNotConnectedToAnyGroupsException;
import im.pupil.api.domain.exception.institution_group.GroupMemberNotFoundException;
import im.pupil.api.domain.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.domain.exception.pupil.PupilNotFoundException;
import im.pupil.api.domain.exception.schedule.ScheduleNotFoundException;
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
    private final PupilRepository pupilRepository;

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
            PupilRepository pupilRepository,
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
        this.pupilRepository = pupilRepository;
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
        this.lessonMapper = lessonMapper;
        this.weekTypeWorker = weekTypeWorker;
        this.scheduleValidator = scheduleValidator;
        this.localDateConverter = localDateConverter;
        this.localTimeConverter = localTimeConverter;
    }

    @Transactional(readOnly = true)
    public GetScheduleWithLessonsDto readScheduleWithLessons(
            Integer id
    ) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) throw new ScheduleNotFoundException();

        List<Lesson> lessons = lessonRepository.findAllLessonsByScheduleId(id);

        GetScheduleWithLessonsDto getScheduleWithLessonsDto = modelMapper.map(
                optionalSchedule.get(), GetScheduleWithLessonsDto.class
        );
        List<GetLessonDto> lessonDtos = lessons.stream()
                .map(m -> modelMapper.map(m, GetLessonDto.class))
                .toList();

        getScheduleWithLessonsDto.setLessons(lessonDtos);
        return getScheduleWithLessonsDto;
    }

    @Transactional(readOnly = true)
    public List<GetLessonWithPassStatusDto> readLessonsWithPassStatusByPrefect(
            String email,
            Integer groupMemberId,
            LocalDate date
    ) {
        try {
            Optional<Pupil> optionalPupil = pupilRepository.findByEmail(email);
            if (optionalPupil.isEmpty()) throw new PupilNotFoundException();

            Pupil pupil = optionalPupil.get();
            Optional<GroupMember> optionalGroupMember = groupMemberRepository.readGroupMemberOfInstitutionByCode(
                    pupil.getCode(), pupil.getInstitution().getId()
            );
            if (optionalGroupMember.isEmpty()) throw new PupilAreNotConnectedToAnyGroupsException();

            GroupMember groupMember = optionalGroupMember.get();
            if (!groupMember.getPrefect()) throw new NotEnoughAccessException();

            return readLessonsWithPassStatus(groupMemberId, date);
        } catch (PupilNotFoundException | PupilAreNotConnectedToAnyGroupsException | NotEnoughAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
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
    public List<GetLessonDto> readCurrentScheduleForPupil(String email, LocalDate currentDate) {
        try {
            Optional<Pupil> optionalPupil = pupilRepository.findByEmail(email);
            if (optionalPupil.isEmpty()) throw new PupilNotFoundException();

            Pupil pupil = optionalPupil.get();
            Optional<GroupMember> optionalGroupMember = groupMemberRepository.readGroupMemberOfInstitutionByCode(
                    pupil.getCode(), pupil.getInstitution().getId()
            );
            if (optionalGroupMember.isEmpty()) throw new PupilAreNotConnectedToAnyGroupsException();

            GroupMember groupMember = optionalGroupMember.get();
            return readCurrentSchedule(groupMember.getGroup().getId(), currentDate);
        } catch (PupilNotFoundException | PupilAreNotConnectedToAnyGroupsException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public List<GetScheduleDto> readSchedulesForPupil(
            String email
    ) throws PupilNotFoundException, PupilAreNotConnectedToAnyGroupsException, UnexpectedException {
        try {
            Optional<Pupil> optionalPupil = pupilRepository.findByEmail(email);
            if (optionalPupil.isEmpty()) throw new PupilNotFoundException();

            Pupil pupil = optionalPupil.get();
            Optional<GroupMember> optionalGroupMember = groupMemberRepository.readGroupMemberOfInstitutionByCode(
                    pupil.getCode(), pupil.getInstitution().getId()
            );
            if (optionalGroupMember.isEmpty()) throw new PupilAreNotConnectedToAnyGroupsException();

            GroupMember groupMember = optionalGroupMember.get();
            return readSchedulesByGroupId(groupMember.getGroup().getId());
        } catch (PupilNotFoundException | PupilAreNotConnectedToAnyGroupsException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
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






















