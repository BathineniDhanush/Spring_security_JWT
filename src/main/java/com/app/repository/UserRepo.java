package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.UserEntity;
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
public Optional<UserEntity> findByUserName(String userName);
}
