package com.yoshi.chat.app.helper;

import org.springframework.stereotype.*;

import java.util.*;

@Component
public class UserCookieValueHelper {

    public String generate() {
//        TODO: とりあえず。。。いつかちゃんと
        return UUID.randomUUID().toString();
    }
}
