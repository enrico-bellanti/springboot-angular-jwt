package com.baseApp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@JsonIgnoreProperties({ "createdAt", "updatedAt"})
@DynamicUpdate
public class Role extends BaseEntity {
    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(  name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public void addPermission(Permission permission){
        if (!permissions.contains(permission)){
            permissions.add(permission);
        }
    }

    public void removePermission(Permission permission){
        if (permissions.contains(permission)){
            permissions.remove(permission);
        }
    }
}
