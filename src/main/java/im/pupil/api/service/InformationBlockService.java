package im.pupil.api.service;

import im.pupil.api.dto.InformationBlockDto;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.exception.relocation.RelocationAlreadyExistsException;
import im.pupil.api.model.InformationBlock;
import im.pupil.api.repository.InformationBlockRepository;
import im.pupil.api.repository.PracticeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InformationBlockService {
    private final InformationBlockRepository informationBlockRepository;
    private final PracticeRepository practiceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public InformationBlockService(InformationBlockRepository informationBlockRepository,
                                   PracticeRepository practiceRepository,
                                   ModelMapper modelMapper) {
        this.informationBlockRepository = informationBlockRepository;
        this.practiceRepository = practiceRepository;
        this.modelMapper = modelMapper;
    }

    public void saveInformationBlockWithPracticeId(InformationBlock informationBlock, Integer practiceId) {
        if (informationBlockRepository.findInformationBlockByTitleAndPractice_Id(informationBlock.getTitle(), practiceId).isPresent()) {
            throw new RelocationAlreadyExistsException(
                    "Information block with name: " +
                            informationBlock.getTitle() +
                            "and practiceId: " + practiceId +
                            " already exists");
        }

        informationBlock.setPractice(
                practiceRepository.findPracticeById(practiceId).orElseThrow(
                        () -> new PracticeNotFoundException("Practice not found with id: " + practiceId))
        );
        informationBlockRepository.save(informationBlock);
    }

    public InformationBlock convertToEntity(InformationBlockDto informationBlockDto) {
        return modelMapper.map(informationBlockDto, InformationBlock.class);
    }

    public InformationBlockDto convertToDto(InformationBlock informationBlock) {
        return modelMapper.map(informationBlock, InformationBlockDto.class);
    }
}
