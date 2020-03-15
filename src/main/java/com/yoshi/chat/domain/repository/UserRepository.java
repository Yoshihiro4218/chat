package com.yoshi.chat.domain.repository;

import com.yoshi.chat.domain.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface UserRepository {
    Optional<User> find(long id);

    Optional<User> findByCookieValue(String cookieValue);

    int create(User user);
}
