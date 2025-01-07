package im.pupil.api.service;

import im.pupil.api.dto.relocation.RelocationDto;
import im.pupil.api.dto.relocation.UpdateRelocationDto;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.exception.relocation.RelocationAlreadyExistsException;
import im.pupil.api.model.Relocation;
import im.pupil.api.repository.PracticeRepository;
import im.pupil.api.repository.RelocationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void updateRelocationWithPracticeId(
            Set<UpdateRelocationDto> relocations,
            Integer practiceId
    ) {
        List<Relocation> savedRelocations = relocationRepository.findAllByPracticeId(practiceId);

        Set<Integer> relocationIds = relocations.stream()
                .map(UpdateRelocationDto::getId)
                .collect(Collectors.toSet());

        List<Relocation> relocationsToDelete = savedRelocations.stream()
                .filter(relocation -> !relocationIds.contains(relocation.getId()))
                .collect(Collectors.toList());

        relocationRepository.deleteAll(relocationsToDelete);

        for (UpdateRelocationDto updateRelocationDto : relocations) {
            Relocation relocation = new Relocation();
            relocation.setName(updateRelocationDto.getName());
            relocation.setPractice(practiceRepository.findPracticeById(practiceId).orElseThrow(
                    () -> new PracticeNotFoundException("Practice not found with id: " + practiceId)
            ));

            if (updateRelocationDto.getId() == null) {
                relocationRepository.save(relocation);
            } else {
                relocation.setId(updateRelocationDto.getId());
                relocationRepository.save(relocation);
            }
        }
    }

    public Relocation convertToEntity(RelocationDto relocationDto) {
        return modelMapper.map(relocationDto, Relocation.class);
    }
}



















