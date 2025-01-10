package im.pupil.api.service;

import im.pupil.api.dto.group.CreateInstitutionGroupDto;
import im.pupil.api.dto.group.GetInstitutionGroup;
import im.pupil.api.dto.group.UpdateInstitutionGroupDto;
import im.pupil.api.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.exception.institution_group.InstitutionGroupWasAddedYearlyException;
import im.pupil.api.exception.speciality.SpecialityNotFoundException;
import im.pupil.api.model.group.GroupMember;
import im.pupil.api.model.group.InstitutionGroup;
import im.pupil.api.model.Speciality;
import im.pupil.api.repository.GroupMemberRepository;
import im.pupil.api.repository.InstitutionGroupRepository;
import im.pupil.api.repository.SpecialityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionGroupService {

    private final GroupMemberService groupMemberService;

    private final InstitutionGroupRepository institutionGroupRepository;
    private final SpecialityRepository specialityRepository;
    private final GroupMemberRepository groupMemberRepository;

    private final ModelMapper modelMapper;

    public InstitutionGroupService(
            GroupMemberService groupMemberService,
            InstitutionGroupRepository institutionGroupRepository,
            SpecialityRepository specialityRepository,
            GroupMemberRepository groupMemberRepository,
            ModelMapper modelMapper
    ) {
        this.groupMemberService = groupMemberService;
        this.institutionGroupRepository = institutionGroupRepository;
        this.specialityRepository = specialityRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<GetInstitutionGroup> readGroupsBySpecialityId(
            Integer id
    ) {
        List<InstitutionGroup> institutionGroups = institutionGroupRepository.readInstitutionGroupsBySpecialityId(id);
        return institutionGroups.stream()
                .map(m -> modelMapper.map(m, GetInstitutionGroup.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GetInstitutionGroup> readGroupByNamePart(
            Integer institutionId,
            String namePart
    ) {
        List<InstitutionGroup> institutionGroups = institutionGroupRepository.findInstitutionGroupByNamePart(namePart, institutionId);
        return institutionGroups.stream()
                .map(m -> modelMapper.map(m, GetInstitutionGroup.class))
                .toList();
    }

    @Transactional
    public void createGroup(
        CreateInstitutionGroupDto createInstitutionGroupDto
    ) {
        Optional<InstitutionGroup> optionalInstitutionGroup = institutionGroupRepository.findInstitutionGroup(
                createInstitutionGroupDto.getSpecialityId(), createInstitutionGroupDto.getName()
        );
        if (optionalInstitutionGroup.isPresent()) throw new InstitutionGroupWasAddedYearlyException();

        Optional<Speciality> optionalSpeciality = specialityRepository.findById(createInstitutionGroupDto.getSpecialityId());
        if (optionalSpeciality.isEmpty()) throw new SpecialityNotFoundException();

        InstitutionGroup institutionGroup = new InstitutionGroup();
        institutionGroup.setCourse(createInstitutionGroupDto.getCourse());
        institutionGroup.setName(createInstitutionGroupDto.getName());
        institutionGroup.setSpeciality(optionalSpeciality.get());
        InstitutionGroup saveGroup = institutionGroupRepository.save(institutionGroup);

        createInstitutionGroupDto.getGroupMemberDtos()
                .forEach(m -> groupMemberService.saveGroupMember(saveGroup.getId(), m));
    }

    @Transactional
    public void updateGroup(
        UpdateInstitutionGroupDto updateInstitutionGroupDto
    ) {
        Optional<InstitutionGroup> optionalInstitutionGroup = institutionGroupRepository.findById(updateInstitutionGroupDto.getId());
        if (optionalInstitutionGroup.isEmpty()) throw new InstitutionGroupNotFoundException();

        InstitutionGroup institutionGroup = optionalInstitutionGroup.get();
        institutionGroup.setCourse(updateInstitutionGroupDto.getCourse());
        institutionGroupRepository.save(institutionGroup);

        groupMemberService.updateGroupMembers(
                institutionGroup.getId(), updateInstitutionGroupDto.getGroupMemberDtos()
        );
    }

    @Transactional
    public void deleteGroup(Integer id) {
        List<GroupMember> groupMembers = groupMemberRepository.readGroupMembers(id);
        groupMemberRepository.deleteAll(groupMembers);
        institutionGroupRepository.deleteById(id);
    }
}




























