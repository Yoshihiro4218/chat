package com.yoshi.chat.domain.entity;

import lombok.*;

import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatLog {
    private Long id;
    private long userId;
    private String message;
    private LocalDateTime chattedAt;
}
