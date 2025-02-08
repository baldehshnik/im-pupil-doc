package im.pupil.api.domain.service.schedule;

import im.pupil.api.domain.dto.group_member.GetGroupMemberPersonalInfoDto;
import im.pupil.api.domain.dto.lesson.GetFullPassDto;
import im.pupil.api.domain.dto.lesson.GetFullPassWithGroupMemberDto;
import im.pupil.api.domain.dto.lesson.GetLessonDto;
import im.pupil.api.domain.dto.lesson.GetWeekDayPassDto;
import im.pupil.api.domain.exception.institution_group.GroupMemberNotFoundException;
import im.pupil.api.domain.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.domain.exception.schedule.ScheduleNotFoundException;
import im.pupil.api.data.entity.group.GroupMember;
import im.pupil.api.data.entity.group.InstitutionGroup;
import im.pupil.api.data.entity.schedule.Pass;
import im.pupil.api.data.entity.schedule.Schedule;
import im.pupil.api.data.repository.GroupMemberRepository;
import im.pupil.api.data.repository.InstitutionGroupRepository;
import im.pupil.api.data.repository.PassRepository;
import im.pupil.api.data.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class PassService {

    private final PassRepository passRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ScheduleRepository scheduleRepository;
    private final InstitutionGroupRepository institutionGroupRepository;

    private final ModelMapper modelMapper;
    private final LocalDateConverter localDateConverter;

    public PassService(
            PassRepository passRepository,
            GroupMemberRepository groupMemberRepository,
            ScheduleRepository scheduleRepository,
            InstitutionGroupRepository institutionGroupRepository,
            ModelMapper modelMapper,
            LocalDateConverter localDateConverter
    ) {
        this.passRepository = passRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.scheduleRepository = scheduleRepository;
        this.institutionGroupRepository = institutionGroupRepository;
        this.modelMapper = modelMapper;
        this.localDateConverter = localDateConverter;
    }

    @Transactional(readOnly = true)
    public List<GetWeekDayPassDto> readPassesCountOfGroupMemberByWeek(
            Integer groupMemberId,
            LocalDate date
    ) {
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(groupMemberId);
        if (optionalGroupMember.isEmpty()) throw new GroupMemberNotFoundException();

        List<Integer> daysOfWeek = IntStream.rangeClosed(1, 7).boxed().toList();

        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        List<Instant> weekDates = IntStream.range(0, 7)
                .mapToObj(startOfWeek::plusDays)
                .map(localDateConverter::convertLocalDateToInstant)
                .toList();

        return daysOfWeek.stream()
                .map(m -> {
                    List<Pass> passes = passRepository.findAllByPupilIdAndDate(
                            optionalGroupMember.get().getId(),
                            weekDates.get(m - 1)
                    );

                    return new GetWeekDayPassDto(m, passes.size());
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GetFullPassDto> readPassesOfGroupMemberByMonth(
            Integer groupMemberId,
            LocalDate date
    ) {
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(groupMemberId);
        if (optionalGroupMember.isEmpty()) throw new GroupMemberNotFoundException();

        int year = date.getYear();
        Month month = date.getMonth();

        int lengthOfMonth = month.length(date.isLeapYear());
        List<LocalDate> daysOfMonth = IntStream.range(0, lengthOfMonth)
                .mapToObj(day -> LocalDate.of(year, month, day + 1))
                .toList();

        List<GetFullPassDto> fullPassDtos = new ArrayList<>();
        for (LocalDate day : daysOfMonth) {
            List<GetFullPassDto> passes = passRepository.findAllByPupilIdAndDate(
                            optionalGroupMember.get().getId(),
                            localDateConverter.convertLocalDateToInstant(day)
                    ).stream()
                    .map(m -> {
                        GetLessonDto getLessonDto = modelMapper.map(m.getLesson(), GetLessonDto.class);
                        return new GetFullPassDto(
                                m.getId(),
                                m.getDate(),
                                getLessonDto
                        );
                    })
                    .toList();

            fullPassDtos.addAll(passes);
        }

        return fullPassDtos;
    }

    @Transactional(readOnly = true)
    public List<GetFullPassDto> readPassesOfGroupMemberBySemester(
            Integer groupMemberId
    ) {
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(groupMemberId);
        if (optionalGroupMember.isEmpty()) throw new GroupMemberNotFoundException();

        Optional<Schedule> currentSchedule = scheduleRepository.findCurrentScheduleByGroupId(optionalGroupMember.get().getGroup().getId());
        if (currentSchedule.isEmpty()) throw new ScheduleNotFoundException();

        LocalDate startDateLocalDate = localDateConverter.convertInstantToLocalDate(currentSchedule.get().getStartDate());
        LocalDate endDateLocalDate = localDateConverter.convertInstantToLocalDate(currentSchedule.get().getFinishDate());

        List<LocalDate> daysBetween = findDaysBetween(startDateLocalDate, endDateLocalDate);

        List<GetFullPassDto> fullPassDtos = new ArrayList<>();
        for (LocalDate day : daysBetween) {
            List<GetFullPassDto> passes = passRepository.findAllByPupilIdAndDate(
                            optionalGroupMember.get().getId(),
                            localDateConverter.convertLocalDateToInstant(day)
                    ).stream()
                    .map(m -> {
                        GetLessonDto getLessonDto = modelMapper.map(m.getLesson(), GetLessonDto.class);
                        return new GetFullPassDto(
                                m.getId(),
                                m.getDate(),
                                getLessonDto
                        );
                    })
                    .toList();

            fullPassDtos.addAll(passes);
        }

        return fullPassDtos;
    }

    @Transactional(readOnly = true)
    public List<GetFullPassWithGroupMemberDto> readPassesOfGroupByWeek(
            Integer groupId,
            LocalDate date
    ) {
        Optional<InstitutionGroup> optionalInstitutionGroup = institutionGroupRepository.findById(groupId);
        if (optionalInstitutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();

        List<GroupMember> groupMembers = groupMemberRepository.readGroupMembers(optionalInstitutionGroup.get().getId());

        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        List<Instant> weekDates = IntStream.range(0, 7)
                .mapToObj(startOfWeek::plusDays)
                .map(localDateConverter::convertLocalDateToInstant)
                .toList();

        List<GetFullPassWithGroupMemberDto> getFullPassWithGroupMemberDtos = new ArrayList<>();
        for (Instant instant : weekDates) {
            for (GroupMember groupMember : groupMembers) {
                GetGroupMemberPersonalInfoDto getGroupMemberPersonalInfoDto = modelMapper.map(groupMember, GetGroupMemberPersonalInfoDto.class);
                List<GetFullPassWithGroupMemberDto> passes = passRepository.findAllByPupilIdAndDate(
                                groupMember.getId(),
                                instant
                        ).stream()
                        .map(m -> {
                            GetLessonDto getLessonDto = modelMapper.map(m.getLesson(), GetLessonDto.class);
                            return new GetFullPassWithGroupMemberDto(
                                    m.getId(),
                                    m.getDate(),
                                    getLessonDto,
                                    getGroupMemberPersonalInfoDto
                            );
                        })
                        .toList();

                getFullPassWithGroupMemberDtos.addAll(passes);
            }
        }

        return getFullPassWithGroupMemberDtos;
    }

    @Transactional(readOnly = true)
    public List<GetFullPassWithGroupMemberDto> readPassesOfGroupByMonth(
            Integer groupId,
            LocalDate date
    ) {
        Optional<InstitutionGroup> optionalInstitutionGroup = institutionGroupRepository.findById(groupId);
        if (optionalInstitutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();

        List<GroupMember> groupMembers = groupMemberRepository.readGroupMembers(optionalInstitutionGroup.get().getId());

        int year = date.getYear();
        Month month = date.getMonth();

        int lengthOfMonth = month.length(date.isLeapYear());
        List<LocalDate> daysOfMonth = IntStream.range(0, lengthOfMonth)
                .mapToObj(day -> LocalDate.of(year, month, day + 1))
                .toList();

        List<GetFullPassWithGroupMemberDto> fullPassDtos = new ArrayList<>();
        for (GroupMember groupMember : groupMembers) {
            GetGroupMemberPersonalInfoDto getGroupMemberPersonalInfoDto = modelMapper.map(groupMember, GetGroupMemberPersonalInfoDto.class);
            for (LocalDate day : daysOfMonth) {
                List<GetFullPassWithGroupMemberDto> passes = passRepository.findAllByPupilIdAndDate(
                                groupMember.getId(),
                                localDateConverter.convertLocalDateToInstant(day)
                        ).stream()
                        .map(m -> {
                            GetLessonDto getLessonDto = modelMapper.map(m.getLesson(), GetLessonDto.class);
                            return new GetFullPassWithGroupMemberDto(
                                    m.getId(),
                                    m.getDate(),
                                    getLessonDto,
                                    getGroupMemberPersonalInfoDto
                            );
                        })
                        .toList();

                fullPassDtos.addAll(passes);
            }
        }

        return fullPassDtos;
    }

    private List<LocalDate> findDaysBetween(LocalDate start, LocalDate end) {
        List<LocalDate> days = new ArrayList<>();

        while (!start.isAfter(end)) {
            days.add(start);
            start = start.plusDays(1);
        }

        return days;
    }
}


























