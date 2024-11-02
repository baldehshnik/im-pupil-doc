package im.pupil.api.service;

import im.pupil.api.exception.refresh.token.RefreshTokenNotFound;
import im.pupil.api.exception.security.sign.up.admin.AdminAlreadyRegisteredException;
import im.pupil.api.model.*;
import im.pupil.api.security.RolesEnum;
import im.pupil.api.security.dto.JwtAuthenticationResponseDto;
import im.pupil.api.security.dto.SignInRequestDto;
import im.pupil.api.security.dto.admin.SignUpAdminRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {
    private final AdminService adminService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EducationalInstitutionService educationalInstitutionService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationService(AdminService adminService,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 EducationalInstitutionService educationalInstitutionService, UserService userService, UserRoleService userRoleService, RoleService roleService, RefreshTokenService refreshTokenService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.educationalInstitutionService = educationalInstitutionService;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.refreshTokenService = refreshTokenService;
    }

    public JwtAuthenticationResponseDto adminSignUp(SignUpAdminRequestDto signUpAdminRequestDto) {
        User user;
        Role role = roleService.findByRoleName(RolesEnum.ADMIN.getDescription());

        UserRole userRole = new UserRole();
        UserRoleId userRoleId = new UserRoleId();

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
        adminService.save(admin);

        String jwtAccess = jwtService.generateToken(adminService.loadAdminUserDetailsByEmail(admin.getUser().getEmail()));
        String jwtRefresh = refreshTokenService.createRefreshToken(signUpAdminRequestDto.getEmail()).getToken();
        return new JwtAuthenticationResponseDto(jwtAccess, jwtRefresh);
    }

    public JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequestDto.getEmail(),
                signInRequestDto.getPassword()
        ));

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
