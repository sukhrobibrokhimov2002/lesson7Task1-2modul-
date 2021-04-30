package uz.sukhrob.lesson7task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.sukhrob.lesson7task1.entity.Role;
import uz.sukhrob.lesson7task1.entity.enums.Permissions;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.payload.RoleDto;
import uz.sukhrob.lesson7task1.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public ApiResponse addRole(RoleDto roleDto) {

        Role role = new Role();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        role.setPermissions(roleDto.getPermissions());
        roleRepository.save(role);
        return new ApiResponse("Successfully added", false);
    }

    public ApiResponse deleteRole(Long id) {
        try {
            Optional<Role> byId = roleRepository.findById(id);
            if (!byId.isPresent()) return new ApiResponse("Role topilmadi", false);
            roleRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }

    public ApiResponse editRole(Long id, RoleDto roleDto) {
        Optional<Role> byId = roleRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("Role not found", false);
        Role role = byId.get();
        role.setName(roleDto.getName());
        role.setPermissions(roleDto.getPermissions());
        role.setDescription(roleDto.getDescription());
        roleRepository.save(role);
        return new ApiResponse("Successfully edited", true);

    }

    public List<Role> getAllRoles() {
        List<Role> all = roleRepository.findAll();
        return all;
    }


}
