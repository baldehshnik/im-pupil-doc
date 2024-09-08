package im.pupil.api.service;

import im.pupil.api.dto.RelocationDto;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.exception.relocation.RelocationAlreadyExistsException;
import im.pupil.api.model.Relocation;
import im.pupil.api.repository.PracticeRepository;
import im.pupil.api.repository.RelocationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RelocationService {
    private final RelocationRepository relocationRepository;
    private final PracticeRepository practiceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RelocationService(RelocationRepository relocationRepository,
                             PracticeRepository practiceRepository,
                             ModelMapper modelMapper) {
        this.relocationRepository = relocationRepository;
        this.practiceRepository = practiceRepository;
        this.modelMapper = modelMapper;
    }

    public void saveRelocationWithPracticeId(Relocation relocation, Integer practiceId) {
        if (relocationRepository.findByNameAndPractice_Id(relocation.getName(), practiceId).isPresent()) {
            throw new RelocationAlreadyExistsException(
                    "Relocation with name: " +
                    relocation.getName() +
                    "and practiceId: " + practiceId +
                    " already exists");
        }

        relocation.setPractice(practiceRepository.findPracticeById(practiceId).orElseThrow(
                () -> new PracticeNotFoundException("Practice not found with id: " + practiceId)
        ));

        relocationRepository.save(relocation);
    }

    public Relocation convertToEntity(RelocationDto relocationDto) {
        return modelMapper.map(relocationDto, Relocation.class);
    }

    public RelocationDto convertToDto(Relocation relocation) {
        return modelMapper.map(relocation, RelocationDto.class);
    }
}
