package im.pupil.api.controller;

import im.pupil.api.dto.AdminDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.exception.admin.response.AdminErrorResponse;
import im.pupil.api.exception.role.RoleNotFoundException;
import im.pupil.api.exception.role.response.RoleErrorResponse;
import im.pupil.api.exception.user.UserNotFoundException;
import im.pupil.api.exception.user.response.UserErrorResponse;
import im.pupil.api.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get admin account by admin ID")
    @ApiResponse(
            responseCode = "200",
            description = "Found the admin account",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminDto.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Admin account not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminErrorResponse.class))
            }
    )
    @GetMapping("/account/search/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    AdminDto getAdminAccountById(@PathVariable Integer id) {
        return adminService.convertToDto(adminService.findAdminById(id));
    }

    @Operation(summary = "Get admin account by admin email")
    @ApiResponse(
            responseCode = "200",
            description = "Found the admin account",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminDto.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Admin account not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Admin account not found, because there is no such user",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "409",
            description = "User doesnt have such a role",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleErrorResponse.class))
            }
    )
    @GetMapping("/account/search/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    AdminDto getAdminAccountByEmail(@PathVariable String email) {
        return adminService.convertToDto(adminService.findAdminByEmail(email));
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleAdminNotFoundException(AdminNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
