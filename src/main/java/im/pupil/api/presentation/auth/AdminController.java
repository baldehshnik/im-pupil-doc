package im.pupil.api.presentation.auth;

import im.pupil.api.domain.dto.SuccessAnswer;
import im.pupil.api.domain.dto.admin.AdminDto;
import im.pupil.api.domain.dto.admin.GetAdminDto;
import im.pupil.api.domain.dto.admin.GetAdminImageDto;
import im.pupil.api.domain.exception.admin.response.AdminErrorResponse;
import im.pupil.api.domain.exception.role.RoleNotFoundException;
import im.pupil.api.domain.exception.role.response.RoleErrorResponse;
import im.pupil.api.domain.exception.user.UserNotFoundException;
import im.pupil.api.domain.exception.user.response.UserErrorResponse;
import im.pupil.api.domain.service.auth.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.UnexpectedException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/icon/change")
    @PreAuthorize("hasRole('ADMIN')")
    public GetAdminImageDto changeAdminIcon(
            @RequestPart("image") @Valid @NotNull MultipartFile image
    ) throws UnexpectedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminService.updateAccountIcon(email, image);
    }

    @GetMapping("/notConfirmed")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetAdminDto> readNotConfirmedAdmins() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminService.readNotConfirmedAdmins(email);
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> confirmAdmin(
            @RequestParam("adminId") Integer adminId
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        adminService.confirmAdminRegistration(email, adminId);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success account confirm!"));
    }

    @PostMapping("/account/update/access/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateAdminAccountAccessMode(
            @PathVariable Integer adminId
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        adminService.updateAdminAccess(email, adminId);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success account access mode updating!"));
    }

    @DeleteMapping("/account/delete/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> deleteAdminAccount(
            @PathVariable Integer adminId
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        adminService.deleteAdminAccount(email, adminId);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success account deleting!"));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetAdminDto> readAdminsByEducationalInstitution() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminService.readAdminsOfEducationInstitution(email);
    }

    @Operation(summary = "Get admin account by admin ID")
    @ApiResponse(
            responseCode = "200",
            description = "Found the admin account",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminDto.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Admin account not found",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminErrorResponse.class))
            }
    )
    @GetMapping("/account/search/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetAdminDto getAdminAccountById(@PathVariable Integer id) {
        return adminService.findAdminById(id);
    }

    @Operation(summary = "Get admin account by admin email")
    @ApiResponse(
            responseCode = "200",
            description = "Found the admin account",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminDto.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Admin account not found",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Admin account not found, because there is no such user",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "409",
            description = "User doesn't have such a role",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleErrorResponse.class))
            }
    )
    @GetMapping("/account/search")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminDto getAdminAccountByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminService.convertToDto(adminService.findAdminByEmail(email));
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        UserErrorResponse response = new UserErrorResponse(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler
    private ResponseEntity<RoleErrorResponse> handleRoleNotFoundException(RoleNotFoundException exception) {
        RoleErrorResponse response = new RoleErrorResponse(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
