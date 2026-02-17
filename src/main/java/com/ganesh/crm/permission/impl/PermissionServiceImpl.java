package com.ganesh.crm.permission.impl;

import com.ganesh.crm.permission.Permission;
import com.ganesh.crm.permission.PermissionDTO;
import com.ganesh.crm.permission.PermissionRepository;
import com.ganesh.crm.permission.PermissionService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;


    //Pendings : I have not handled that Duplicate permission here (if permission is duplicate - not make sense)
    @Override
    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        Permission permission = modelMapper.map(permissionDTO, Permission.class);
        Permission permissionSaved = permissionRepository.save(permission);
        return modelMapper.map(permissionSaved, PermissionDTO.class);
    }

    @Override
    public List<PermissionDTO> permissionDetails() {

        List<Permission> permissionList = permissionRepository.findAll();

       return permissionList.stream().map(each -> {
           PermissionDTO permissionDTO = modelMapper.map(each, PermissionDTO.class);
           return permissionDTO;
       }).toList();
    }

    @Override
    public PermissionDTO updatePermissions(Long id, PermissionDTO permissionDTO) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id NOT FOUND!"));

        permission.setModuleName(permissionDTO.getModuleName());
        permission.setAction(permissionDTO.getAction());

        Permission saveToDb = permissionRepository.save(permission);

        return modelMapper.map(saveToDb, PermissionDTO.class);

    }
}
