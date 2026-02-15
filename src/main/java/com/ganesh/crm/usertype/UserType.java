package com.ganesh.crm.usertype;

import com.ganesh.crm.permission.Permission;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "user_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type" ,nullable = false, unique = true)
    private  String typeName;            //ADMIN, SALES

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_type_permissions",
    joinColumns = @JoinColumn(name = "user_type_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;
}
