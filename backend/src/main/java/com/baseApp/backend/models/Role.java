package com.baseApp.backend.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;
}
