package com.ganesh.crm.permission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO {
    private Long id;
    private  String moduleName;
    private String action;
}
