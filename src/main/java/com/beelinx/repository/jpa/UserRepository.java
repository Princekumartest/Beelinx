package com.beelinx.repository.jpa;

import com.beelinx.model.UserEntity;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

        UserEntity findByEmail(String email);
}