package com.realworld.v1.global.utils;

import com.realworld.v1.global.code.NickName;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class CommonUtil {
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        if ((obj instanceof String) && (((String) obj).trim().length() == 0)) {
            return true;
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        if (obj instanceof List) {
            return ((List<?>) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            return (((Object[]) obj).length == 0);
        }

        return false;
    }

    public static boolean passwordValidationCheck(String password) {
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", password);
    }

    public static boolean userIdValidationCheck(String userId) {
        return Pattern.matches("^[a-z0-9]{6,12}$", userId);
    }

    public static String createNickname() {
        NickName nickName = new NickName();
        String[] determiners = nickName.getDeterminers();
        int length = determiners.length;
        String determiner = determiners[(int) (Math.random() * length)];
        String[] nicknames = nickName.getName();
        String name = nicknames[(int) (Math.random() * nicknames.length)];
        String nickname = determiner + " " + name;
        log.info(nickname);
        return nickname;
    }
}
