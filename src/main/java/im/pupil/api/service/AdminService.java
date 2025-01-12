package im.pupil.api.service;

import im.pupil.api.dto.admin.AdminDto;
import im.pupil.api.dto.admin.GetAdminDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.exception.admin.NotEnoughAccessException;
import im.pupil.api.model.Admin;
import im.pupil.api.model.User;
import im.pupil.api.repository.AdminRepository;
import im.pupil.api.repository.UserRepository;
import im.pupil.api.security.RolesEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final UserRoleService userRoleService;

    private final ModelMapper modelMapper;

    private final RoleService roleService;

    @Autowired
    public AdminService(
            AdminRepository adminRepository,
            UserService userService,
            ModelMapper modelMapper,
            UserRoleService userRoleService,
            RoleService roleService,
            UserRepository userRepository
    ) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<GetAdminDto> readAdminsOfEducationInstitution(String email) {
        Admin admin = findAdminByEmail(email);
        List<Admin> allAdmins = adminRepository.findByInstitution_Id(admin.getInstitution().getId());
        allAdmins.remove(admin);

        return allAdmins.stream()
                .map(m -> new GetAdminDto(
                        m.getId(),
                        m.getFirstname(),
                        m.getLastname(),
                        m.getPatronymic(),
                        m.getUser().getEmail(),
                        m.getAccessMode(),
                        m.getIcon()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public Admin findAdminByEmail(String email) {
        User user = userService.findByEmail(email);
        Integer idOfRole = roleService.findByRoleName(RolesEnum.ADMIN.getDescription()).getId();
        boolean isUserHaveAdminRole = userRoleService.existsByUserIdAndRoleId(user.getId(), idOfRole);

        if (!isUserHaveAdminRole) {
            throw new AdminNotFoundException("No admin found with email: " + email);
        }

        return findAdminByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public GetAdminDto findAdminById(Integer id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isEmpty()) throw new AdminNotFoundException("No admin found with id: " + id);

        Admin admin = optionalAdmin.get();
        return new GetAdminDto(
                admin.getId(),
                admin.getFirstname(),
                admin.getLastname(),
                admin.getPatronymic(),
                admin.getUser().getEmail(),
                admin.getAccessMode(),
                admin.getIcon()
        );
    }

    @Transactional
    public void deleteAdminAccount(String email, Integer deletingAccountId) {
        Admin admin = findAdminByEmail(email);
        GetAdminDto deletingAdmin = findAdminById(deletingAccountId);
        User deletingUser = userService.findByEmail(deletingAdmin.getEmail());

        if (admin.getAccessMode() > deletingAdmin.getAccessMode()) {
            adminRepository.deleteById(deletingAdmin.getId());
            userRepository.deleteById(deletingUser.getId());
        } else {
            throw new NotEnoughAccessException();
        }
    }

    @Transactional
    public void updateAdminAccess(String email, Integer updatingAccountId) {
        Admin admin = findAdminByEmail(email);
        Optional<Admin> optionalUpdatingAdmin = adminRepository.findById(updatingAccountId);
        if (optionalUpdatingAdmin.isEmpty()) throw new AdminNotFoundException("No admin found with id: " + updatingAccountId);

        Admin updatingAdmin = optionalUpdatingAdmin.get();
        if (admin.getAccessMode() > updatingAdmin.getAccessMode()) {
            updatingAdmin.setAccessMode(updatingAdmin.getAccessMode() + 1);
            adminRepository.save(updatingAdmin);
        } else {
            throw new NotEnoughAccessException();
        }
    }

    private Admin findAdminByUserId(Integer userId) {
        Optional<Admin> admin = adminRepository.findByUserId(userId);

        return admin.orElseThrow(() -> new AdminNotFoundException("No admin found with userId: " + userId));
    }

    public boolean existsByUserId(Integer userId) {
        return adminRepository.existsByUserId(userId);
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public UserDetails loadAdminUserDetailsByEmail(String email) throws UsernameNotFoundException {
        Admin admin = findAdminByEmail(email);
        return userService.loadUserByUsername(admin.getUser().getEmail());
    }

    public AdminDto convertToDto(Admin admin) {
        return modelMapper.map(admin, AdminDto.class);
    }

    public Admin convertToEntity(AdminDto adminDto) {
        return modelMapper.map(adminDto, Admin.class);
    }
}
