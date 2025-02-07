package im.pupil.api.service.exam;

import im.pupil.api.dto.exam.AddExamDto;
import im.pupil.api.dto.exam.DeleteExamsDto;
import im.pupil.api.dto.exam.GetExamDto;
import im.pupil.api.dto.exam.UpdateExamDto;
import im.pupil.api.exception.exam.ExamAlreadyExistsException;
import im.pupil.api.exception.exam.ExamNotFoundException;
import im.pupil.api.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.model.exam.Exam;
import im.pupil.api.model.group.InstitutionGroup;
import im.pupil.api.repository.ExamRepository;
import im.pupil.api.repository.InstitutionGroupRepository;
import im.pupil.api.service.schedule.LocalDateConverter;
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

    private final ModelMapper modelMapper;
    private final LocalDateConverter localDateConverter;

    public ExamService(
            ExamRepository examRepository,
            InstitutionGroupRepository institutionGroupRepository,
            ModelMapper modelMapper,
            LocalDateConverter localDateConverter
    ) {
        this.examRepository = examRepository;
        this.institutionGroupRepository = institutionGroupRepository;
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



























