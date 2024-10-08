package com.beelinx.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String mobile_Number;
    private String email;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
