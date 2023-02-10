package com.baseApp.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@TypeDef(name = "json", typeClass = JsonStringType.class)
@DynamicUpdate
public class Notification extends BaseEntity {
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @JsonBackReference
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "notifiable_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_notifications_fk"
            )
    )
    private User notifiable;

    @Column(name = "data", columnDefinition = "json")
    @Type(type = "json")
    private Object data;

    @Column(name = "read_at")
    private LocalDateTime read_at;

    public Notification(String type, User notifiable, Object data) {
        this.type = type;
        this.notifiable = notifiable;
        this.data = data;
    }
}
