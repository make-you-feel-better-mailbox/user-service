package com.onetwo.userservice.common;

public class GlobalUrl {

    public static final String ROOT_URI = "/";
    public static final String UNDER_ROUTE = "/*";
    public static final String EVERY_UNDER_ROUTE = "/**";

    public static final String USER_ROOT = "/users";
    public static final String USER_LOGIN = USER_ROOT + "/login";
    public static final String USER_ID = USER_ROOT + "/id";
    public static final String USER_PW = USER_ROOT + "/pw";
    public static final String TOKEN = "/token";
    public static final String TOKEN_REFRESH = TOKEN + "/refresh";
}
