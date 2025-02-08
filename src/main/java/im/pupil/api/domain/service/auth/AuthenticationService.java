package im.pupil.api.domain.service.auth;

import im.pupil.api.data.entity.*;
import im.pupil.api.domain.dto.pupil.AddPupilDto;
import im.pupil.api.domain.service.*;
import im.pupil.api.domain.exception.admin.AdminNotConfirmedYetException;
import im.pupil.api.domain.exception.pupil.PupilAlreadyRegisteredException;
import im.pupil.api.domain.exception.pupil.PupilNotConfirmedYetException;
import im.pupil.api.domain.exception.pupil.PupilNotFoundException;
import im.pupil.api.domain.exception.refresh.token.RefreshTokenNotFound;
import im.pupil.api.domain.exception.security.sign.up.admin.AdminAlreadyRegisteredException;
import im.pupil.api.domain.exception.user.IncorrectDataException;
import im.pupil.api.domain.exception.user.UserNotFoundException;
import im.pupil.api.data.entity.institution.EducationalInstitution;
import im.pupil.api.data.repository.AdminRepository;
import im.pupil.api.data.repository.PupilRepository;
import im.pupil.api.data.repository.UserRepository;
import im.pupil.api.presentation.security.model.RolesEnum;
import im.pupil.api.presentation.security.model.JwtAuthenticationResponseDto;
import im.pupil.api.presentation.security.model.SignInRequestDto;
import im.pupil.api.presentation.security.model.SignUpAdminRequestDto;
import im.pupil.api.service.*;
import im.pupil.api.domain.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PupilRepository pupilRepository;

    private final PupilService pupilService;
    private final NotificationService notificationService;
    private final AdminService adminService;
    private final JwtService jwtService;
    private final EducationalInstitutionService educationalInstitutionService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(
            AdminRepository adminRepository,
            UserRepository userRepository,
            PupilRepository pupilRepository,
            PupilService pupilService,
            NotificationService notificationService,
            AdminService adminService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            EducationalInstitutionService educationalInstitutionService,
            UserService userService,
            UserRoleService userRoleService,
            RoleService roleService,
            RefreshTokenService refreshTokenService
    ) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.pupilRepository = pupilRepository;
        this.pupilService = pupilService;
        this.notificationService = notificationService;
        this.adminService = adminService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.educationalInstitutionService = educationalInstitutionService;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public JwtAuthenticationResponseDto pupilSignIn(SignInRequestDto signInRequestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(signInRequestDto.getEmail());
        if (optionalUser.isEmpty()) throw new UserNotFoundException();

        if (!passwordEncoder.matches(signInRequestDto.getPassword(), optionalUser.get().getPassword())) {
            throw new IncorrectDataException();
        }

        Optional<Pupil> optionalPupil = pupilRepository.findByEmail(signInRequestDto.getEmail());
        if (optionalPupil.isEmpty()) throw new PupilNotFoundException();
        if (pupilRepository.findRegisteredById(optionalPupil.get().getId()).isEmpty())
            throw new PupilNotConfirmedYetException();

        UserDetails userDetails = userService.loadUserByUsername(signInRequestDto.getEmail());
        String jwtAccess = jwtService.generateToken(userDetails);
        String jwtRefresh = refreshTokenService.findByUserId(userService.findByEmail(signInRequestDto.getEmail()).getId())
                .orElseThrow(() -> new RefreshTokenNotFound("This user have no refresh token"))
                .getToken();

        return new JwtAuthenticationResponseDto(jwtAccess, jwtRefresh);
    }

    @Transactional
    public void pupilSignUp(
            AddPupilDto addPupilDto
    ) {
        Role role = roleService.findByRoleName(RolesEnum.USER.getDescription());
        UserRole userRole = new UserRole();
        UserRoleId userRoleId = new UserRoleId();

        User user;
        if (userService.existsByEmail(addPupilDto.getEmail())) {
            user = userService.findByEmail(addPupilDto.getEmail());
        } else {
            User forSave = new User();
            forSave.setEmail(addPupilDto.getEmail());
            forSave.setPassword(passwordEncoder.encode(addPupilDto.getPassword()));
            user = userService.save(forSave);
        }

        if (!userRoleService.existsByUserIdAndRoleId(user.getId(), role.getId())) {
            userRoleId.setRoleId(role.getId());
            userRoleId.setUserId(user.getId());

            userRole.setId(userRoleId);
            userRole.setRole(role);
            userRole.setUser(user);
            userRoleService.save(userRole);
        } else {
            userRole = userRoleService.findByUserIdAndRoleId(user.getId(), role.getId());
        }
        user.setUserRoles(Set.of(userRole));

        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(addPupilDto.getInstitutionId());
        if (pupilService.existsByUserId(user.getId())) throw new PupilAlreadyRegisteredException();
        if (pupilService.existsByCodeAndInstitutionId(addPupilDto.getCode(), educationalInstitution.getId()))
            throw new PupilAlreadyRegisteredException();

        Pupil pupil = new Pupil();
        pupil.setCode(addPupilDto.getCode());
        pupil.setInstitution(educationalInstitution);
        pupil.setUser(user);
        pupil.setStatus(-1);
        pupilRepository.save(pupil);

        refreshTokenService.createRefreshToken(addPupilDto.getEmail());
        notificationService.addNewPupilNotification(pupil.getCode(), educationalInstitution);
    }

    @Transactional
    public JwtAuthenticationResponseDto adminSignUp(SignUpAdminRequestDto signUpAdminRequestDto) {
        Role role = roleService.findByRoleName(RolesEnum.ADMIN.getDescription());
        UserRole userRole = new UserRole();
        UserRoleId userRoleId = new UserRoleId();

        User user;
        if (userService.existsByEmail(signUpAdminRequestDto.getEmail())) {
            user = userService.findByEmail(signUpAdminRequestDto.getEmail());
        } else {
            user = userService.save(
                    User.builder()
                            .email(signUpAdminRequestDto.getEmail())
                            .password(passwordEncoder.encode(signUpAdminRequestDto.getPassword()))
                            .build());
        }

        if (!userRoleService.existsByUserIdAndRoleId(user.getId(), role.getId())) {
            userRoleId.setRoleId(role.getId());
            userRoleId.setUserId(user.getId());

            userRole.setId(userRoleId);
            userRole.setRole(role);
            userRole.setUser(user);
            userRoleService.save(userRole);

        } else {
            userRole = userRoleService.findByUserIdAndRoleId(user.getId(), role.getId());
        }
        user.setUserRoles(Set.of(userRole));

        EducationalInstitution educationalInstitution = educationalInstitutionService.
                findEducationalInstitutionByAbbreviation(signUpAdminRequestDto.getEducationalInstitutionAbbreviation());

        Admin admin;
        if (adminService.existsByUserId(user.getId())) {
            throw new AdminAlreadyRegisteredException("Admin with such email already registered");
        } else {
            admin = Admin.builder()
                    .firstname(signUpAdminRequestDto.getFirstname())
                    .lastname(signUpAdminRequestDto.getLastname())
                    .patronymic(signUpAdminRequestDto.getPatronymic())
                    .accessMode(1)
                    .build();
        }

        admin.setUser(user);
        admin.setInstitution(educationalInstitution);
        admin.setStatus(-1);
        adminRepository.save(admin);

        String jwtAccess = jwtService.generateToken(adminService.loadAnyAdminUserDetailsByEmail(admin.getUser().getEmail()));
        String jwtRefresh = refreshTokenService.createRefreshToken(signUpAdminRequestDto.getEmail()).getToken();

        notificationService.addNewAdminNotification(admin.getFirstname(), admin.getLastname(), educationalInstitution);

        return new JwtAuthenticationResponseDto(jwtAccess, jwtRefresh);
    }

    @Transactional
    public JwtAuthenticationResponseDto adminSignIn(SignInRequestDto signInRequestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(signInRequestDto.getEmail());
        if (optionalUser.isEmpty()) throw new UserNotFoundException();

        if (!passwordEncoder.matches(signInRequestDto.getPassword(), optionalUser.get().getPassword())) {
            throw new IncorrectDataException();
        }

        Optional<Admin> optionalAdmin = adminRepository.findRegisteredAdminByEmail(signInRequestDto.getEmail());
        if (optionalAdmin.isEmpty()) throw new AdminNotConfirmedYetException();

        UserDetails userDetails = userService.loadUserByUsername(signInRequestDto.getEmail());
        String jwtAccess = jwtService.generateToken(userDetails);
        String jwtRefresh = refreshTokenService.findByUserId(
                        userService.findByEmail(
                                        signInRequestDto.getEmail())
                                .getId())
                .orElseThrow(() -> new RefreshTokenNotFound("This user have no refresh token"))
                .getToken();
        return new JwtAuthenticationResponseDto(jwtAccess, jwtRefresh);
    }
}











