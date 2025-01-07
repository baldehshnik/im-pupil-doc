package im.pupil.api.service;

import im.pupil.api.dto.information_block.InformationBlockDto;
import im.pupil.api.dto.information_block.UpdateInformationBlock;
import im.pupil.api.exception.information.block.InformationBlockAlreadyExistsException;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.model.InformationBlock;
import im.pupil.api.repository.InformationBlockRepository;
import im.pupil.api.repository.PracticeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class InformationBlockService {

    private final InformationBlockRepository informationBlockRepository;
    private final PracticeRepository practiceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public InformationBlockService(
            InformationBlockRepository informationBlockRepository,
            PracticeRepository practiceRepository,
            ModelMapper modelMapper
    ) {
        this.informationBlockRepository = informationBlockRepository;
        this.practiceRepository = practiceRepository;
        this.modelMapper = modelMapper;
    }

    public void saveInformationBlockWithPracticeId(InformationBlock informationBlock, Integer practiceId) {
        if (informationBlockRepository.findInformationBlockByTitleAndPractice_Id(informationBlock.getTitle(), practiceId).isPresent()) {
            throw new InformationBlockAlreadyExistsException(
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

    public void updateInformationBlockWithPracticeId(
            Set<UpdateInformationBlock> updateInformationBlocks,
            Integer practiceId
    ) {
        List<InformationBlock> informationBlocks = informationBlockRepository.findAllByPracticeId(practiceId);

        Set<Integer> informationBlockIds = updateInformationBlocks.stream()
                .map(UpdateInformationBlock::getId)
                .collect(Collectors.toSet());

        List<InformationBlock> informationBlockToDelete = informationBlocks.stream()
                .filter(informationBlock -> !informationBlockIds.contains(informationBlock.getId()))
                .collect(Collectors.toList());

        informationBlockRepository.deleteAll(informationBlockToDelete);

        for (UpdateInformationBlock updateInformationBlock : updateInformationBlocks) {
            InformationBlock informationBlock = new InformationBlock();
            informationBlock.setTitle(updateInformationBlock.getTitle());
            informationBlock.setContent(updateInformationBlock.getContent());
            informationBlock.setPractice(practiceRepository.findPracticeById(practiceId).orElseThrow(
                    () -> new PracticeNotFoundException("Practice not found with id: " + practiceId)
            ));

            if (updateInformationBlock.getId() == null) {
                informationBlockRepository.save(informationBlock);
            } else {
                informationBlock.setId(updateInformationBlock.getId());
                informationBlockRepository.save(informationBlock);
            }
        }
    }

    public InformationBlock convertToEntity(InformationBlockDto informationBlockDto) {
        return modelMapper.map(informationBlockDto, InformationBlock.class);
    }
}

















