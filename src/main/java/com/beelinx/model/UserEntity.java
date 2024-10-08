package com.beelinx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "users", schema = "student")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_number")
    @NotBlank(message = "Please enter a valid mobile number")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only alphabetic characters")
   // @Pattern(regexp = "^(\\+?\\d{1,3}[- ]?)?\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobile_Number;

    @Column(name = "email_address", unique = true)
    private String email;

    @Column(name = "password")
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt=" + updatedAt +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }

}
