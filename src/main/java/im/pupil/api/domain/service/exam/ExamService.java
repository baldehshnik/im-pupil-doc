package im.pupil.api.domain.service.exam;

import im.pupil.api.data.entity.Pupil;
import im.pupil.api.data.entity.exam.Exam;
import im.pupil.api.data.entity.group.GroupMember;
import im.pupil.api.data.entity.group.InstitutionGroup;
import im.pupil.api.data.repository.ExamRepository;
import im.pupil.api.data.repository.GroupMemberRepository;
import im.pupil.api.data.repository.InstitutionGroupRepository;
import im.pupil.api.data.repository.PupilRepository;
import im.pupil.api.domain.dto.exam.AddExamDto;
import im.pupil.api.domain.dto.exam.DeleteExamsDto;
import im.pupil.api.domain.dto.exam.GetExamDto;
import im.pupil.api.domain.dto.exam.UpdateExamDto;
import im.pupil.api.domain.exception.UnexpectedException;
import im.pupil.api.domain.exception.exam.ExamAlreadyExistsException;
import im.pupil.api.domain.exception.exam.ExamNotFoundException;
import im.pupil.api.domain.exception.exam.PupilAreNotConnectedToAnyGroupsException;
import im.pupil.api.domain.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.domain.exception.pupil.PupilNotFoundException;
import im.pupil.api.domain.service.schedule.LocalDateConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final InstitutionGroupRepository institutionGroupRepository;
    private final PupilRepository pupilRepository;
    private final GroupMemberRepository groupMemberRepository;

    private final ModelMapper modelMapper;
    private final LocalDateConverter localDateConverter;

    public ExamService(
            ExamRepository examRepository,
            InstitutionGroupRepository institutionGroupRepository,
            PupilRepository pupilRepository,
            GroupMemberRepository groupMemberRepository,
            ModelMapper modelMapper,
            LocalDateConverter localDateConverter
    ) {
        this.examRepository = examRepository;
        this.institutionGroupRepository = institutionGroupRepository;
        this.pupilRepository = pupilRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.modelMapper = modelMapper;
        this.localDateConverter = localDateConverter;
    }

    @Transactional
    public void deleteExams(
            DeleteExamsDto deleteExamsDto
    ) {
        deleteExamsDto.getIds().forEach(examRepository::deleteById);
    }

    @Transactional(readOnly = true)
    public GetExamDto readExamById(
            Integer id
    ) {
        Optional<Exam> optionalExam = examRepository.findById(id);
        if (optionalExam.isEmpty()) throw new ExamNotFoundException();

        return modelMapper.map(optionalExam.get(), GetExamDto.class);
    }

    @Transactional
    public List<GetExamDto> readExams(
            String email,
            LocalDate localDate
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
            List<Exam> oldExams = examRepository.readExamsByGroupId(groupMember.getGroup().getId());
            for (Exam exam : oldExams) {
                Instant instant = exam.getDateTime();
                LocalDate examDate = localDateConverter.convertInstantToLocalDate(instant);

                if (localDate.isAfter(examDate)) {
                    exam.setStatus(-1);
                    examRepository.save(exam);
                }
            }

            List<Exam> currentExams = examRepository.readExamsByGroupId(groupMember.getGroup().getId());
            return currentExams.stream()
                    .map(m -> modelMapper.map(m, GetExamDto.class))
                    .toList();
        } catch (PupilNotFoundException | PupilAreNotConnectedToAnyGroupsException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public List<GetExamDto> readExams(
            Integer groupId,
            LocalDate localDate
    ) {
        List<Exam> oldExams = examRepository.readExamsByGroupId(groupId);
        for (Exam exam : oldExams) {
            Instant instant = exam.getDateTime();
            LocalDate examDate = localDateConverter.convertInstantToLocalDate(instant);

            if (localDate.isAfter(examDate)) {
                exam.setStatus(-1);
                examRepository.save(exam);
            }
        }

        List<Exam> currentExams = examRepository.readExamsByGroupId(groupId);
        return currentExams.stream()
                .map(m -> modelMapper.map(m, GetExamDto.class))
                .toList();
    }

    @Transactional
    public void createExam(
            AddExamDto addExamDto
    ) {
        Optional<InstitutionGroup> optionalInstitutionGroup = institutionGroupRepository.findById(addExamDto.getGroupId());
        if (optionalInstitutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();

        Optional<Exam> existsExam = examRepository.readExam(addExamDto.getName(), addExamDto.getGroupId());
        if (existsExam.isPresent()) throw new ExamAlreadyExistsException();

        Exam exam = modelMapper.map(addExamDto, Exam.class);
        exam.setStatus(0);
        exam.setGroup(optionalInstitutionGroup.get());
        examRepository.save(exam);
    }

    @Transactional
    public void updateExam(
            UpdateExamDto updateExamDto
    ) {
        Optional<Exam> existsExam = examRepository.findById(updateExamDto.getId());
        if (existsExam.isEmpty()) throw new ExamNotFoundException();

        Exam exam = modelMapper.map(updateExamDto, Exam.class);
        exam.setGroup(existsExam.get().getGroup());
        examRepository.save(exam);
    }
}



























