package im.pupil.api.domain.service.statistics;

import im.pupil.api.data.repository.PassRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final PassRepository passRepository;

    public StatisticsService(
            PassRepository passRepository
    ) {
        this.passRepository = passRepository;
    }


}































