package com.david.crudjwt.exceptions;

public class ItemsNotFoundException extends Exception
{
    public ItemsNotFoundException(String message, Class<?> obj)
    {
        super(message+" Threw on "+obj.getName());
    }
}
