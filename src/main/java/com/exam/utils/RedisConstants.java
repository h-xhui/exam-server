package com.exam.utils;

public class RedisConstants {
    public static final String LOGIN_CODE_KEY = "login:code:";

    public static final Long LOGIN_CODE_TTL = 5L;

    public static final String USER_KEY = "exam:user:";
    public static final Long USER_TTL = 30L;

    public static final String USER_TOKEN = "exam:user:token:";

    public static final Long CACHE_NULL_TTL = 2L;
}
