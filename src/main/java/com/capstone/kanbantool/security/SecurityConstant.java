package com.capstone.kanbantool.security;

public class SecurityConstant {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";

    public static final String SECRET = "UsedAsSecretKeyForJWTs";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static final long TOKEN_EXPIRATION_TIME = 1800_000;

}
