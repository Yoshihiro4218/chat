package com.yoshi.chat.domain.repository;

import com.yoshi.chat.domain.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface ChatLogRepository {
    List<ChatLog> findAll();

    List<ChatLog> findLimit(int limit);

    int create(ChatLog chatLog);
}
