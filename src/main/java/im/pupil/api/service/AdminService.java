package im.pupil.api.service;

import im.pupil.api.dto.AdminDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.model.Admin;
import im.pupil.api.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminService {
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }

    public Admin findAdminByEmail(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);

        return admin.orElseThrow(() -> new AdminNotFoundException("No admin found with email: " + email));
    }

    public Admin findAdminById(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);

        return admin.orElseThrow(() -> new AdminNotFoundException("No admin found with id: " + id));
    }

    public AdminDto convertToDto(Admin admin) {
        return modelMapper.map(admin, AdminDto.class);
    }

    public Admin convertToEntity(AdminDto adminDto) {
        return modelMapper.map(adminDto, Admin.class);
    }
}
