package im.pupil.api.domain.service;

import im.pupil.api.domain.dto.speciality.GetSpecialityDto;
import im.pupil.api.data.entity.institution.Speciality;
import im.pupil.api.data.repository.SpecialityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecialityService {

    private final SpecialityRepository specialityRepository;

    private final ModelMapper modelMapper;

    public SpecialityService(
            SpecialityRepository specialityRepository,
            ModelMapper modelMapper
    ) {
        this.specialityRepository = specialityRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<GetSpecialityDto> readSpecialitiesByFacultyId(
            Integer id
    ) {
        List<Speciality> specialities = specialityRepository.readSpecialityByFacultyId(id);
        return specialities.stream()
                .map(m -> modelMapper.map(m, GetSpecialityDto.class))
                .toList();
    }
}




























