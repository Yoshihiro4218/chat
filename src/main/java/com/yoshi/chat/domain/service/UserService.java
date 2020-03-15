package com.yoshi.chat.domain.service;

import com.yoshi.chat.domain.entity.*;
import com.yoshi.chat.domain.repository.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> find(long id) {
        return userRepository.find(id);
    }

    public Optional<User> findByCookieValue(String cookieValue) {
        return userRepository.findByCookieValue(cookieValue);
    }

    public int create(String cookieValue, String userName) {
        return userRepository.create(User.builder()
                                         .cookieValue(cookieValue)
                                         .userName(userName)
                                         .issued_at(LocalDateTime.now())
                                         .build());
    }
}
