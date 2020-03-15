package com.yoshi.chat.app.common.util;

import lombok.experimental.*;

import javax.servlet.http.*;

@UtilityClass
public class RetrieveCookieUtil {

    public static String retrieveValue(Cookie[] cookies, String cookieName) {
//            TODO: ダサいけどあとでfixをします。。
        if (cookies == null) {
            throw new RuntimeException("No Cookie.");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        throw new RuntimeException("No Target Cookie.");
    }
}
