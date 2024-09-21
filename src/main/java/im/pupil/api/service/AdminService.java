package im.pupil.api.service;

import im.pupil.api.dto.AdminDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.model.Admin;
import im.pupil.api.model.User;
import im.pupil.api.repository.AdminRepository;
import im.pupil.api.security.RolesEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;
    private final RoleService roleService;

    @Autowired
    public AdminService(AdminRepository adminRepository, UserService userService, ModelMapper modelMapper, UserRoleService userRoleService, RoleService roleService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
    }

    public Admin findAdminByEmail(String email) {
        User user = userService.findByEmail(email);
        Integer idOfRole = roleService.findByRoleName(RolesEnum.ADMIN.getDescription()).getId();
        boolean isUserHaveAdminRole = userRoleService.existsByUserIdAndRoleId(user.getId(), idOfRole);

        if (!isUserHaveAdminRole) {
            throw new AdminNotFoundException("No admin found with email: " + email);
        }

        return findAdminByUserId(user.getId());
    }

    public Admin findAdminById(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);

        return admin.orElseThrow(() -> new AdminNotFoundException("No admin found with id: " + id));
    }

    public Admin findAdminByUserId(Integer userId) {
        Optional<Admin> admin = adminRepository.findByUserId(userId);

        return admin.orElseThrow(() -> new AdminNotFoundException("No admin found with userId: " + userId));
    }

    public AdminDto convertToDto(Admin admin) {
        return modelMapper.map(admin, AdminDto.class);
    }

    public Admin convertToEntity(AdminDto adminDto) {
        return modelMapper.map(adminDto, Admin.class);
    }
}
