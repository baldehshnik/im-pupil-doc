package im.pupil.api.domain.service.auth;

import im.pupil.api.data.entity.Pupil;
import im.pupil.api.data.entity.group.GroupMember;
import im.pupil.api.data.repository.GroupMemberRepository;
import im.pupil.api.data.repository.PupilRepository;
import im.pupil.api.domain.dto.group.GroupInfoDto;
import im.pupil.api.domain.dto.pupil.GetPupilDto;
import im.pupil.api.domain.dto.pupil.OnlyPupilDto;
import im.pupil.api.domain.dto.pupil.ReadPupilAccountDto;
import im.pupil.api.domain.exception.UnexpectedException;
import im.pupil.api.domain.exception.exam.PupilAreNotConnectedToAnyGroupsException;
import im.pupil.api.domain.exception.pupil.PupilNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PupilService {

    private final PupilRepository pupilRepository;
    private final GroupMemberRepository groupMemberRepository;

    private final ModelMapper modelMapper;

    public PupilService(
            PupilRepository pupilRepository,
            GroupMemberRepository groupMemberRepository,
            ModelMapper modelMapper
    ) {
        this.pupilRepository = pupilRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public ReadPupilAccountDto readPupilAccount(String email) {
        try {
            Optional<Pupil> optionalPupil = pupilRepository.findByEmail(email);
            if (optionalPupil.isEmpty()) throw new PupilNotFoundException();

            Pupil pupil = optionalPupil.get();
            Optional<GroupMember> optionalGroupMember = groupMemberRepository.readGroupMemberOfInstitutionByCode(
                    pupil.getCode(), pupil.getInstitution().getId()
            );
            if (optionalGroupMember.isEmpty()) return null;

            GroupMember groupMember = optionalGroupMember.get();
            ReadPupilAccountDto readPupilAccountDto = new ReadPupilAccountDto();
            readPupilAccountDto.setId(pupil.getId());
            readPupilAccountDto.setFirstname(groupMember.getFirstname());
            readPupilAccountDto.setLastname(groupMember.getLastname());
            readPupilAccountDto.setPatronymic(groupMember.getPatronymic());
            readPupilAccountDto.setPrefect(groupMember.getPrefect());
            readPupilAccountDto.setCode(groupMember.getCode());
            readPupilAccountDto.setPupil(modelMapper.map(pupil, OnlyPupilDto.class));

            List<GroupMember> groupMembers = groupMemberRepository.readGroupMembers(groupMember.getGroup().getId());
            GroupInfoDto groupInfoDto = new GroupInfoDto(
                    groupMember.getGroup().getSpeciality().getFaculty().getInstitution().getAbbreviation(),
                    groupMember.getGroup().getSpeciality().getFaculty().getInstitution().getName(),
                    groupMember.getGroup().getSpeciality().getFaculty().getInstitution().getAddress(),
                    groupMember.getGroup().getSpeciality().getFaculty().getInstitution().getPhone(),
                    groupMember.getGroup().getSpeciality().getName(),
                    groupMember.getGroup().getName(),
                    groupMembers.size()
            );

            readPupilAccountDto.setGroupInfo(groupInfoDto);
            return readPupilAccountDto;
        } catch (PupilNotFoundException | PupilAreNotConnectedToAnyGroupsException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public List<GetPupilDto> readNotConfirmedPupils(
            Integer institutionId
    ) {
        List<Pupil> pupils = pupilRepository.findNotRegisteredByInstitutionId(institutionId);
        return pupils.stream()
                .map(m -> modelMapper.map(m, GetPupilDto.class))
                .toList();
    }

    @Transactional
    public void confirmPupil(
            Integer id
    ) {
        Optional<Pupil> optionalPupil = pupilRepository.findById(id);
        if (optionalPupil.isEmpty()) throw new PupilNotFoundException();

        Pupil pupil = optionalPupil.get();
        pupil.setStatus(1);
        pupilRepository.save(pupil);

        Optional<GroupMember> optionalGroupMember = groupMemberRepository.readGroupMemberOfInstitutionByCode(
                pupil.getCode(),
                pupil.getInstitution().getId()
        );
        if (optionalGroupMember.isPresent()) {
            GroupMember groupMember = optionalGroupMember.get();
            groupMember.setPupil(pupil);
            groupMemberRepository.save(groupMember);
        }
    }

    public boolean existsByUserId(Integer id) {
        Optional<Pupil> optionalPupil = pupilRepository.findByUserId(id);
        return optionalPupil.isPresent();
    }

    public boolean existsByCodeAndInstitutionId(String code, Integer id) {
        Optional<Pupil> optionalPupil = pupilRepository.findNotRegisteredByCodeAndInstitutionId(code, id);
        return optionalPupil.isPresent();
    }

    public Pupil findById(Integer id) {
        Optional<Pupil> pupil = pupilRepository.findById(id);

        return pupil.orElseThrow(() -> new PupilNotFoundException("No pupil with id " + id));
    }
}

























