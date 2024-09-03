package im.pupil.api.controller;

import im.pupil.api.dto.AdminDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.exception.admin.response.AdminErrorResponse;
import im.pupil.api.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                    schema = @Schema(implementation = AdminDto.class))
            }
    )
    @GetMapping("/account/search/id/{id}")
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
                    schema = @Schema(implementation = AdminDto.class))
            }
    )
    @GetMapping("/account/search/email/{email}")
    AdminDto getAdminAccountByEmail(@PathVariable String email) {
        return adminService.convertToDto(adminService.findAdminByEmail(email));
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleAdminNotFoundException(AdminNotFoundException exception) {
        AdminErrorResponse responseEntity = new AdminErrorResponse(exception.getMessage());

        return new ResponseEntity<>(responseEntity, HttpStatus.NOT_FOUND);
    }
}
