package im.pupil.api.service;

import im.pupil.api.exception.role.RoleNotFoundException;
import im.pupil.api.model.UserRole;
import im.pupil.api.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public boolean existsByUserIdAndRoleId(Integer userId, Integer roleId) {
        return userRoleRepository.existsByUserIdAndRoleId(userId, roleId);
    }

    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public UserRole findByUserIdAndRoleId(Integer userId, Integer roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId)
                .orElseThrow(() -> new RoleNotFoundException("User: " + userId + "doesn't have role with id: "+ roleId));
    }
}
