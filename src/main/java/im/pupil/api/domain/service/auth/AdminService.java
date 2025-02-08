package im.pupil.api.domain.service.auth;

import im.pupil.api.domain.image.storage.ImageWorker;
import im.pupil.api.domain.dto.admin.AdminDto;
import im.pupil.api.domain.dto.admin.GetAdminDto;
import im.pupil.api.domain.dto.admin.GetAdminImageDto;
import im.pupil.api.domain.exception.admin.AdminNotConfirmedYetException;
import im.pupil.api.domain.exception.admin.AdminNotFoundException;
import im.pupil.api.domain.exception.admin.NotEnoughAccessException;
import im.pupil.api.data.entity.Admin;
import im.pupil.api.data.entity.User;
import im.pupil.api.data.repository.AdminRepository;
import im.pupil.api.data.repository.UserRepository;
import im.pupil.api.domain.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.UnexpectedException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final ImageWorker imageWorker;

    public AdminService(
            AdminRepository adminRepository,
            UserService userService,
            ModelMapper modelMapper,
            UserRepository userRepository,
            ImageWorker imageWorker
    ) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.imageWorker = imageWorker;
    }

    @Transactional
    public GetAdminImageDto updateAccountIcon(
            String email,
            MultipartFile image
    ) throws UnexpectedException {
        Admin admin = findAdminByEmail(email);
        String newUrl = admin.getIcon();

        if (!image.isEmpty()) {
            String url = imageWorker.saveImage(image, ImageWorker.ImageType.ACCOUNT);
            admin.setIcon(url);
            newUrl = adminRepository.save(admin).getIcon();
        }

        return new GetAdminImageDto(newUrl);
    }

    @Transactional(readOnly = true)
    public List<GetAdminDto> readNotConfirmedAdmins(
            String email
    ) {
        Admin admin = findAdminByEmail(email);
        List<Admin> allAdmins = adminRepository.findNotConfirmedAdmins(admin.getInstitution().getId());
        return allAdmins.stream()
                .map(m -> {
                    GetAdminDto getAdminDto = modelMapper.map(m, GetAdminDto.class);
                    getAdminDto.setEmail(m.getUser().getEmail());
                    return getAdminDto;
                })
                .toList();
    }

    @Transactional
    public void confirmAdminRegistration(
            String email,
            Integer id
    ) {
        Admin admin = findAdminByEmail(email);
        Optional<Admin> optionalConfirmAdmin = adminRepository.findById(id);
        if (optionalConfirmAdmin.isEmpty()) throw new AdminNotFoundException();
        if (admin.getAccessMode() < 2) throw new NotEnoughAccessException();

        Admin confirmAdmin = optionalConfirmAdmin.get();
        confirmAdmin.setStatus(1);
        adminRepository.save(confirmAdmin);
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
        return findAdminByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public GetAdminDto findAdminById(Integer id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isEmpty()) throw new AdminNotFoundException("No admin found with id: " + id);
        if (optionalAdmin.get().getStatus() != 1) throw new AdminNotConfirmedYetException();

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
        if (optionalUpdatingAdmin.isEmpty())
            throw new AdminNotFoundException("No admin found with id: " + updatingAccountId);
        if (optionalUpdatingAdmin.get().getStatus() != 1) throw new AdminNotConfirmedYetException();

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
        if (admin.isEmpty()) throw new AdminNotFoundException("No admin found with userId: " + userId);
        if (admin.get().getStatus() != 1) throw new AdminNotConfirmedYetException();

        return admin.get();
    }

    public boolean existsByUserId(Integer userId) {
        return adminRepository.existsByUserId(userId);
    }

    public UserDetails loadAnyAdminUserDetailsByEmail(String email) throws UsernameNotFoundException {
        Optional<Admin> optionalAdmin = adminRepository.findAdminByEmail(email);
        if (optionalAdmin.isEmpty()) throw new AdminNotFoundException();

        return userService.loadUserByUsername(optionalAdmin.get().getUser().getEmail());
    }

    public AdminDto convertToDto(Admin admin) {
        return modelMapper.map(admin, AdminDto.class);
    }

    public Admin convertToEntity(AdminDto adminDto) {
        return modelMapper.map(adminDto, Admin.class);
    }
}















