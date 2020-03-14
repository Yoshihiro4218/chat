package com.yoshi.chat.domain.service;

import com.yoshi.chat.domain.entity.*;
import com.yoshi.chat.domain.repository.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class ChatLogService {
    private final ChatLogRepository chatLogRepository;

    public List<ChatLog> findAllLimit(int limit) {
        return chatLogRepository.findAllLimit(limit);
    }

    public int create(long userId, String message) {
        return chatLogRepository.create(ChatLog.builder()
                                               .userId(userId)
                                               .message(message)
                                               .build());
    }
}
