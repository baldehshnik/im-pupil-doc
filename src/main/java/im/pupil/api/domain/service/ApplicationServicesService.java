package im.pupil.api.domain.service;

import im.pupil.api.data.repository.ServiceRepository;
import im.pupil.api.domain.dto.services.ReadServiceDto;
import im.pupil.api.domain.exception.UnexpectedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationServicesService {

    private final ServiceRepository serviceRepository;

    private final ModelMapper modelMapper;

    public ApplicationServicesService(ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<ReadServiceDto> readServices() {
        try {
            return serviceRepository.readAllEnabled().stream()
                    .map(m -> modelMapper.map(m, ReadServiceDto.class))
                    .toList();
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }
}


























