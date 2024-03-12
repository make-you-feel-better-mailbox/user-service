package com.onetwo.userservice.common;

public class GlobalUrl {

    public static final String ROOT_URI = "/user-service";
    public static final String UNDER_ROUTE = "/*";
    public static final String EVERY_UNDER_ROUTE = "/**";

    public static final String USER_ROOT = ROOT_URI + "/users";
    public static final String USER_LOGIN = USER_ROOT + "/login";
    public static final String USER_ID = USER_ROOT + "/id";
    public static final String USER_PW = USER_ROOT + "/pw";
    public static final String TOKEN = ROOT_URI + "/token";
    public static final String TOKEN_REFRESH = TOKEN + "/refresh";

    public static final String OAUTH_ROOT = USER_LOGIN + "/oauth";
}
