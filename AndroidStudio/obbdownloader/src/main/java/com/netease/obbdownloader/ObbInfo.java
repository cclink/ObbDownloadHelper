package com.netease.obbdownloader;

public class ObbInfo {

    public static final int MAIN_EXPANSION_FILE_VERSION = 3;
    public static final long MAIN_EXPANSION_FILE_SIZE = 8139694L;

    public static final int PATCH_EXPANSION_FILE_VERSION = 0;
    public static final long PATCH_EXPANSION_FILE_SIZE = 0;

    // stuff for LVL -- MODIFY FOR YOUR APPLICATION!
    public static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5bDiV+USOTQttOF3cwAW/wYASmSnCpcIwgeogDbD0pyipA9vJ05Ma+JloNqHWQJyPHXtQQEpZPhV1veRfPVnmRweL3+ujQ6kLZsRzBjvkA0AfKsaWsk8Jl3qcNEbkoA9YrILEO8lXcEq/8MVhRKy1TgGrevXr6ooaXvV0gA0crE3LX1y+XcZvPkP2c9pnusBnY0z9TwS/P3OuyNgxzKPC4VYDfr2bhxaOOuHdpeVTdXZse6oIYkjOo3tePeHxt5I10H82XN4BREju32cDjqLL2/LdYJhgBXT3xU0YTOjcI2ViaJnabVS5RCqKbze2pFL8gqhwK3iUh/+6mdLNAH1iwIDAQAB";

    // used by the preference obfuscater
    public static final byte[] SALT = new byte[] { 1, 43, -122, -11, 4, 8, -33, -12, 43, 12, -2, -4, 9, 5, -52, -108, -33, 45, -1, 84 };
}
