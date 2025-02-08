package im.pupil.api.domain.service.schedule;

import im.pupil.api.data.repository.*;
import im.pupil.api.domain.dto.group_member.EducationPlaceDto;
import im.pupil.api.domain.dto.group_member.GetGroupMemberDto;
import im.pupil.api.domain.dto.lesson.*;
import im.pupil.api.domain.exception.institution_group.GroupMemberNotFoundException;
import im.pupil.api.domain.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.domain.exception.schedule.GroupMemberAlreadyHasAPassException;
import im.pupil.api.domain.exception.schedule.LessonNotFoundException;
import im.pupil.api.domain.exception.schedule.ScheduleNotFoundException;
import im.pupil.api.data.entity.institution.EducationalInstitution;
import im.pupil.api.data.entity.institution.Faculty;
import im.pupil.api.data.entity.institution.Speciality;
import im.pupil.api.data.entity.group.GroupMember;
import im.pupil.api.data.entity.group.InstitutionGroup;
import im.pupil.api.data.entity.schedule.Lesson;
import im.pupil.api.data.entity.schedule.Pass;
import im.pupil.api.data.entity.schedule.Schedule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ScheduleRepository scheduleRepository;
    private final InstitutionGroupRepository institutionGroupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final PassRepository passRepository;

    private final ModelMapper modelMapper;

    private final LocalTimeConverter localTimeConverter;

    public LessonService(
            LessonRepository lessonRepository,
            ScheduleRepository scheduleRepository,
            InstitutionGroupRepository institutionGroupRepository,
            GroupMemberRepository groupMemberRepository,
            PassRepository passRepository,
            ModelMapper modelMapper,
            LocalTimeConverter localTimeConverter
    ) {
        this.lessonRepository = lessonRepository;
        this.scheduleRepository = scheduleRepository;
        this.institutionGroupRepository = institutionGroupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.passRepository = passRepository;
        this.modelMapper = modelMapper;
        this.localTimeConverter = localTimeConverter;
    }

    @Transactional(readOnly = true)
    public List<GetGroupMemberWithPass> readGroupMembersWithPassStatus(
            Integer groupId,
            Integer lessonId,
            LocalDate date
    ) {
        Optional<InstitutionGroup> optionalInstitutionGroup = institutionGroupRepository.findById(groupId);
        if (optionalInstitutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();

        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isEmpty()) throw new LessonNotFoundException();

        List<GroupMember> groupMembers = groupMemberRepository.readGroupMembers(groupId);
        List<GetGroupMemberDto> getGroupMemberDtos = groupMembers.stream()
                .map(m -> modelMapper.map(m, GetGroupMemberDto.class))
                .toList();

        List<GetGroupMemberWithPass> getGroupMemberWithPasses = new ArrayList<>(groupMembers.size());
        InstitutionGroup institutionGroup = optionalInstitutionGroup.get();
        Speciality speciality = institutionGroup.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        EducationalInstitution educationalInstitution = faculty.getInstitution();
        EducationPlaceDto educationPlaceDto = new EducationPlaceDto(
                educationalInstitution.getName(),
                faculty.getName(),
                institutionGroup.getName()
        );
        getGroupMemberDtos.forEach(m -> {
            Optional<Pass> optionalPass = passRepository.readPass(
                    localTimeConverter.convertLocalTimeToInstant(optionalLesson.get().getStart(), date),
                    optionalLesson.get().getId(),
                    m.getId()
            );

            GetGroupMemberDto getGroupMemberDto = modelMapper.map(m, GetGroupMemberDto.class);
            getGroupMemberDto.setEducationPlaceDto(educationPlaceDto);

            if (optionalPass.isEmpty()) {
                GetPassDto getPassDto = new GetPassDto(
                        null,
                        localTimeConverter.convertLocalTimeToInstant(optionalLesson.get().getStart(), date),
                        -1
                );
                getGroupMemberWithPasses.add(
                        new GetGroupMemberWithPass(
                                getGroupMemberDto,
                                getPassDto
                        )
                );
            } else {
                getGroupMemberWithPasses.add(
                        new GetGroupMemberWithPass(
                                getGroupMemberDto,
                                modelMapper.map(optionalPass.get(), GetPassDto.class)
                        )
                );
            }
        });

        return getGroupMemberWithPasses;
    }

    @Transactional(readOnly = true)
    public GetGroupMemberWithPass readGroupMemberWithPassStatus(
            Integer groupMemberId,
            Integer lessonId,
            LocalDate date
    ) {
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(groupMemberId);
        if (optionalGroupMember.isEmpty()) throw new GroupMemberNotFoundException();

        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isEmpty()) throw new LessonNotFoundException();

        Optional<Pass> optionalPass = passRepository.readPass(
                localTimeConverter.convertLocalTimeToInstant(optionalLesson.get().getStart(), date),
                optionalLesson.get().getId(),
                optionalGroupMember.get().getId()
        );

        InstitutionGroup institutionGroup = optionalGroupMember.get().getGroup();
        Speciality speciality = institutionGroup.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        EducationalInstitution educationalInstitution = faculty.getInstitution();
        EducationPlaceDto educationPlaceDto = new EducationPlaceDto(
                educationalInstitution.getName(),
                faculty.getName(),
                institutionGroup.getName()
        );

        GetGroupMemberDto getGroupMemberDto = modelMapper.map(optionalGroupMember.get(), GetGroupMemberDto.class);
        getGroupMemberDto.setEducationPlaceDto(educationPlaceDto);
        if (optionalPass.isEmpty()) {
            GetPassDto getPassDto = new GetPassDto(
                    null,
                    localTimeConverter.convertLocalTimeToInstant(optionalLesson.get().getStart(), date),
                    -1
            );
            return new GetGroupMemberWithPass(
                    getGroupMemberDto,
                    getPassDto
            );
        } else {
            return new GetGroupMemberWithPass(
                    getGroupMemberDto,
                    modelMapper.map(optionalPass.get(), GetPassDto.class)
            );
        }
    }

    @Transactional
    public void updatePassesStatus(
            UpdatePassesStatusDto updatePassesStatusDto
    ) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(updatePassesStatusDto.getLessonId());
        if (optionalLesson.isEmpty()) throw new LessonNotFoundException();

        Instant instant = localTimeConverter.convertLocalTimeToInstant(optionalLesson.get().getStart(), updatePassesStatusDto.getDate());
        for (UpdatePassesStatusDto.UpdateInfo m : updatePassesStatusDto.getUpdateInfos()) {
            Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(m.getGroupMemberId());
            if (optionalGroupMember.isEmpty()) throw new GroupMemberNotFoundException();

            if (m.getId() != null) {
                Optional<Pass> optionalPass = passRepository.findById(m.getId());
                if (optionalPass.isPresent()) {
                    Pass pass = optionalPass.get();
                    pass.setStatus(m.getStatus());
                    passRepository.save(pass);
                    continue;
                }
            }

            Optional<Pass> existingPass = passRepository.readPass(
                    instant,
                    optionalLesson.get().getId(),
                    optionalGroupMember.get().getId()
            );
            if (existingPass.isPresent()) throw new GroupMemberAlreadyHasAPassException();

            Pass pass = new Pass();
            pass.setStatus(m.getStatus());
            pass.setPupil(optionalGroupMember.get());
            pass.setLesson(optionalLesson.get());
            pass.setDate(instant);
            passRepository.save(pass);
        }
    }

    @Transactional
    public void updatePassStatus(
            UpdatePassStatusDto updatePassStatusDto
    ) {
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(updatePassStatusDto.getGroupMemberId());
        if (optionalGroupMember.isEmpty()) throw new GroupMemberNotFoundException();

        Optional<Lesson> optionalLesson = lessonRepository.findById(updatePassStatusDto.getLessonId());
        if (optionalLesson.isEmpty()) throw new LessonNotFoundException();

        if (updatePassStatusDto.getId() != null) {
            Optional<Pass> optionalPass = passRepository.findById(updatePassStatusDto.getId());
            if (optionalPass.isPresent()) {
                Pass pass = optionalPass.get();
                pass.setStatus(updatePassStatusDto.getStatus());
                passRepository.save(pass);
                return;
            }
        }

        Optional<Pass> existingPass = passRepository.readPass(
                localTimeConverter.convertLocalTimeToInstant(optionalLesson.get().getStart(), updatePassStatusDto.getDate()),
                optionalLesson.get().getId(),
                optionalGroupMember.get().getId()
        );
        if (existingPass.isPresent()) throw new GroupMemberAlreadyHasAPassException();

        Pass pass = new Pass();
        pass.setStatus(updatePassStatusDto.getStatus());
        pass.setPupil(optionalGroupMember.get());
        pass.setLesson(optionalLesson.get());
        pass.setDate(localTimeConverter.convertLocalTimeToInstant(optionalLesson.get().getStart(), updatePassStatusDto.getDate()));
        passRepository.save(pass);
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

































