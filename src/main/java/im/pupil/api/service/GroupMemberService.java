package im.pupil.api.service;

import im.pupil.api.dto.group_member.*;
import im.pupil.api.exception.institution_group.GroupMemberNotFoundException;
import im.pupil.api.exception.institution_group.GroupMemberWasAddedYearlyException;
import im.pupil.api.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.model.institution.EducationalInstitution;
import im.pupil.api.model.institution.Faculty;
import im.pupil.api.model.institution.Speciality;
import im.pupil.api.model.group.GroupMember;
import im.pupil.api.model.group.InstitutionGroup;
import im.pupil.api.repository.GroupMemberRepository;
import im.pupil.api.repository.InstitutionGroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final InstitutionGroupRepository institutionGroupRepository;

    private final ModelMapper modelMapper;

    public GroupMemberService(
            GroupMemberRepository groupMemberRepository,
            InstitutionGroupRepository institutionGroupRepository,
            ModelMapper modelMapper
    ) {
        this.groupMemberRepository = groupMemberRepository;
        this.institutionGroupRepository = institutionGroupRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public CanBeAddedGroupMemberDto canBeAddedCheck(
            Integer groupId,
            AddGroupMemberDto addGroupMemberDto
    ) {
        Optional<GroupMember> groupMember = groupMemberRepository.readGroupMember(addGroupMemberDto.getCode(), groupId);
        return new CanBeAddedGroupMemberDto(groupMember.isEmpty());
    }

    @Transactional
    public void makeAPrefect(
            Integer memberId
    ) {
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(memberId);
        if (optionalGroupMember.isEmpty()) throw new GroupMemberNotFoundException();

        GroupMember newGroupMember = optionalGroupMember.get();
        List<GroupMember> allMembers = groupMemberRepository.readGroupMembers(newGroupMember.getGroup().getId());
        allMembers.forEach(m -> {
            if (m.getPrefect()) {
                m.setPrefect(false);
                groupMemberRepository.save(m);
            }
        });

        newGroupMember.setPrefect(true);
        groupMemberRepository.save(newGroupMember);
    }

    @Transactional(readOnly = true)
    public List<GetGroupMemberDto> readGroupMembers(
        Integer groupId
    ) {
        List<GroupMember> groupMembers = groupMemberRepository.readGroupMembers(groupId);
        return groupMembers.stream()
                .map(m -> modelMapper.map(m, GetGroupMemberDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public GetGroupMemberDto readGroupMemberById(
            Integer id
    ) {
        Optional<GroupMember> groupMember = groupMemberRepository.findById(id);
        if (groupMember.isEmpty()) throw new GroupMemberNotFoundException();

        InstitutionGroup institutionGroup = groupMember.get().getGroup();
        Speciality speciality = institutionGroup.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        EducationalInstitution educationalInstitution = faculty.getInstitution();
        EducationPlaceDto educationPlaceDto = new EducationPlaceDto(
                educationalInstitution.getName(),
                faculty.getName(),
                institutionGroup.getName()
        );

        GetGroupMemberDto getGroupMemberDto = modelMapper.map(groupMember, GetGroupMemberDto.class);
        getGroupMemberDto.setEducationPlaceDto(educationPlaceDto);

        return getGroupMemberDto;
    }

    @Transactional
    public void saveGroupMember(
            Integer groupId,
            CreateNewGroupMemberDto addGroupMemberDto
    ) {
        GroupMember groupMember = new GroupMember();
        groupMember.setFirstname(addGroupMemberDto.getFirstname());
        groupMember.setLastname(addGroupMemberDto.getLastname());
        groupMember.setPatronymic(addGroupMemberDto.getPatronymic());
        groupMember.setCode(addGroupMemberDto.getCode());

        Optional<InstitutionGroup> institutionGroup = institutionGroupRepository.findById(groupId);
        if (institutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();
        groupMember.setGroup(institutionGroup.get());

        if (groupMemberRepository.readGroupMember(addGroupMemberDto.getCode(), groupId).isPresent()) {
            throw new GroupMemberWasAddedYearlyException();
        }

        groupMemberRepository.save(groupMember);
    }

    @Transactional
    public void updateGroupMembers(
            Integer groupId,
            List<AddGroupMemberDto> groupMemberDtos
    ) {
        List<GroupMember> savedGroupMembers = groupMemberRepository.readGroupMembers(groupId);
        Set<Integer> groupMemberIds = groupMemberDtos.stream()
                .map(AddGroupMemberDto::getId)
                .collect(Collectors.toSet());

        List<GroupMember> groupMembersToDelete = savedGroupMembers.stream()
                .filter(groupMember -> !groupMemberIds.contains(groupMember.getId()))
                .toList();

        groupMemberRepository.deleteAll(groupMembersToDelete);

        for (AddGroupMemberDto addGroupMemberDto : groupMemberDtos) {
            if (addGroupMemberDto.getId() == null) {
                saveGroupMember(groupId, modelMapper.map(addGroupMemberDto, CreateNewGroupMemberDto.class));
            } else {
                updateGroupMember(groupId, addGroupMemberDto);
            }
        }
    }

    private void updateGroupMember(
            Integer groupId,
            AddGroupMemberDto groupMemberDto
    ) {
        GroupMember groupMember = new GroupMember();
        groupMember.setId(groupMemberDto.getId());
        groupMember.setFirstname(groupMemberDto.getFirstname());
        groupMember.setLastname(groupMemberDto.getLastname());
        groupMember.setPatronymic(groupMemberDto.getPatronymic());
        groupMember.setCode(groupMemberDto.getCode());

        Optional<InstitutionGroup> institutionGroup = institutionGroupRepository.findById(groupId);
        if (institutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();
        groupMember.setGroup(institutionGroup.get());

        groupMemberRepository.save(groupMember);
    }
}































