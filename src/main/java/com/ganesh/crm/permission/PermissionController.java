package com.ganesh.crm.permission;

import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    @PostMapping("/permission")
    public ResponseEntity<PermissionDTO> createPermission (@RequestBody PermissionDTO permissionDTO) {
        PermissionDTO permissionDTO1 = permissionService.createPermission(permissionDTO);
        return new ResponseEntity<>(permissionDTO1, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    @GetMapping
    public ResponseEntity<List<PermissionDTO>> allPermissionDetails() {
        List<PermissionDTO> permissionDTOS = permissionService.permissionDetails();
        return ResponseEntity.ok(permissionDTOS);

    }


    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    @PutMapping("/{id}")
    public  ResponseEntity<PermissionDTO> updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        PermissionDTO permissionDTO1 = permissionService.updatePermissions(id, permissionDTO);
        return ResponseEntity.ok(permissionDTO1);
    }
}
