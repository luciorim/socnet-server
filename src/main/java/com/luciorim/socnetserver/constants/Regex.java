package com.luciorim.socnetserver.constants;

import java.util.regex.Pattern;

public class Regex {
    private static final String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    public static final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    public static final Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

}
