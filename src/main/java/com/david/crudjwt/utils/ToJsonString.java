package com.david.crudjwt.utils;

public abstract class ToJsonString
{
    public static String toJsonString(String value)
    {
        return "\"" + value + "\"";
    }
}
