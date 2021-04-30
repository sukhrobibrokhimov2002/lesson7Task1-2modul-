package uz.sukhrob.lesson7task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sukhrob.lesson7task1.entity.Role;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.payload.RoleDto;
import uz.sukhrob.lesson7task1.service.RoleService;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;


    @PreAuthorize(value = "hasAuthority(ADD_ROLE)")
    @PostMapping
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.addRole(roleDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }
    @PreAuthorize(value = "hasAuthority(DELETE_ROLE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = roleService.deleteRole(id);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }
    @PreAuthorize(value = "hasAuthority(EDIT_ROLE)")
    @PatchMapping
    public ResponseEntity<?> edit(@RequestParam Long id, @Valid @RequestParam RoleDto roleDto) {
        ApiResponse apiResponse = roleService.editRole(id, roleDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);

    }
    @PreAuthorize(value = "hasAuthority(VIEW_ROLE)")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Role> allRoles = roleService.getAllRoles();
        if (allRoles.isEmpty()) return ResponseEntity.status(409).body(allRoles);
        return ResponseEntity.ok(allRoles);
    }
}
