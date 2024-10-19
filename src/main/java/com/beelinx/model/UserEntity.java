package com.beelinx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "full_name", unique = false)
    private String fullName;

    @Column(name = "mobile_number")//, unique = true
    //@NotBlank(message = "Mobile number is required")
    //@Pattern(regexp = "^([+]\\d{2})?\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Column(name = "mobile_otp")
    private String mobileOtp;

    @Column(name = "mobile_otp_verified")
    private boolean mobileOtpVerified = false;

    @Column(name = "email_otp_verified")
    private boolean emailOtpVerified = false;

   // @NotBlank(message = "Email is required")
    @Column(name = "email_address")//, unique = true
    @Email(message = "invalid email address")
    private String email;

    @Column(name = "email_otp")
    private String emailOtp;

    @Column(name = "password")
   // @NotBlank(message = "Password is required")
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*]).{8,}$",
     //       message = "Password must be at least 8 characters uppercase lowercase number special character")
    private String password;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    private LocalDateTime forEmailOtp;

    private LocalDateTime forMobileOtp;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt=" + updatedAt +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
