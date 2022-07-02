package br.com.lets.code.moviesbattle.security;

public class SecurityConstants {

    public static final String SECRET = "71e6607a9a36c0bbe3535d3fb2d876863950349d5d9144464169157e0799e138";
    public static final long EXPIRATION_TIME = 900_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/login";
}
