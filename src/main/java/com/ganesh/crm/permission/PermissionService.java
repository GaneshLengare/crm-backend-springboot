package com.ganesh.crm.permission;


import java.util.List;

public interface PermissionService {

    PermissionDTO createPermission(PermissionDTO permissionDTO);


    List<PermissionDTO> permissionDetails();

    //Doubht? :I am taking id to update the permission -> While we want to update the permissions how we know that this id has this permission
    PermissionDTO updatePermissions(Long id, PermissionDTO permissionDTO);

    // no need to delete the permission because in future if we want to assign the usertype to that permission!
}
