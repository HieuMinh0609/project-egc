package com.example.file.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private Long id;
    private String name;
    private String phone;

    @DateTimeFormat(pattern = "kk:mm, dd/MM/yyyy")
    private LocalDateTime createDate;
}
