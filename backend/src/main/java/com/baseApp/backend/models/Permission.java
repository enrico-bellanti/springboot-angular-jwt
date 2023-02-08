package com.baseApp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
@JsonIgnoreProperties({ "createdAt", "updatedAt"})
@DynamicUpdate
public class Permission extends BaseEntity {
    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "category", length = 40, nullable = false)
    private String category;
}
