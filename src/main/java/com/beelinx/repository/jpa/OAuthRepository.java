package com.beelinx.repository.jpa;

import com.beelinx.model.OAuthEntity;
import com.beelinx.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthRepository extends JpaRepository<OAuthEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    OAuthEntity findByEmail(String email);

}