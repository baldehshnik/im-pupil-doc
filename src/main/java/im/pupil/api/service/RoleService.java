package im.pupil.api.service;

import im.pupil.api.exception.role.RoleNotFoundException;
import im.pupil.api.model.Role;
import im.pupil.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found with name " + roleName));
    }

    public Role findById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + id));
    }
}
