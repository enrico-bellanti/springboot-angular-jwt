package com.baseApp.backend.repositories;

import com.baseApp.backend.models.Notification;
import com.baseApp.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query("select n from Notification n where n.notifiable = ?1 ORDER BY createdAt DESC")
    Page<Notification> findAllWhereNotifiableId(Pageable pageable, User notifiable);

    @Query("select count(*) from Notification n where n.notifiable = ?1 and n.read_at = null")
    Long countUnReadByNotifiableId(User notifiable);

    @Query("SELECT n FROM Notification n where n.notifiable = ?1 and n.read_at = null")
    List<Notification> findAllUnReadByNotifiableId(User notifiable);

}
