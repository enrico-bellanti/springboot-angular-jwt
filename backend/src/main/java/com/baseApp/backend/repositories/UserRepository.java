package com.baseApp.backend.repositories;

import com.baseApp.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByIdAndDeletedAtIsNull(UUID id);

    @Override
    @Query("select u from User u where u.deletedAt = null")
    Page<User> findAll(Pageable pageable);

    @Query("select u from User u")
    Page<User> findAllWithBin(Pageable pageable);

    @Query("select u from User u where u.deletedAt is not null")
    Page<User> findAllInBin(Pageable pageable);
}
