package com.david.crudjwt.exceptions;

public class ItemCannotBeSavedException extends Exception
{
    public ItemCannotBeSavedException(String message,Class<?> obj)
    {
        super(message+" Threw on "+obj.getName());
    }
}
