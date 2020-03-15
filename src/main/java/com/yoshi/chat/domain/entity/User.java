package com.yoshi.chat.domain.entity;

import lombok.*;

import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    @NonNull
    private String cookieValue;
    @NonNull
    private String userName;
    @NonNull
    private LocalDateTime issued_at;
}
