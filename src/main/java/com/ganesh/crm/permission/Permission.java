package com.ganesh.crm.permission;

import com.ganesh.crm.usertype.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "permissions", uniqueConstraints = @UniqueConstraint(columnNames = {"moduleName", "action"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String moduleName;    // USER, CUSTOMER

    @Column(nullable = false)
    private String action;       //CREATE, READ, UPDATE, DELETE

    /*@ManyToMany(mappedBy = "permissions")
    private Set<UserType> userTypes;  */ // one permission belongs to many types of user
}
