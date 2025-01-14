package im.pupil.api.service.auth;

import im.pupil.api.dto.pupil.GetPupilDto;
import im.pupil.api.exception.pupil.PupilNotFoundException;
import im.pupil.api.model.Pupil;
import im.pupil.api.model.group.GroupMember;
import im.pupil.api.repository.GroupMemberRepository;
import im.pupil.api.repository.PupilRepository;
import im.pupil.api.service.UserService;
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

    private final UserService userService;

    private final ModelMapper modelMapper;

    public PupilService(
            PupilRepository pupilRepository,
            GroupMemberRepository groupMemberRepository,
            UserService userService,
            ModelMapper modelMapper
    ) {
        this.pupilRepository = pupilRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        ;
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

























