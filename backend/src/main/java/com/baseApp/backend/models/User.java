package com.baseApp.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@DynamicUpdate
public class User extends BaseEntity {

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    @Email
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "preferred_lang", length = 5)
    private Locale preferredLang;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = false;

    @Column(name = "deleted_at")
    private Date deletedAt;

    //Relations
    @ManyToMany
    @JoinTable(  name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "notifiable",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Notification> notifications = new HashSet<>();

    /*public User(String firstName, String lastName, String email, String password, String phone, Locale preferredLang, Set<Role> roles) {
    }*/

    public void addRole(Role role){
        if (!this.roles.contains(role)){
            this.roles.add(role);
        }
    }

    public void removeRole(Role role){
        if (this.roles.contains(role)){
            this.roles.remove(role);
        }
    }
}
