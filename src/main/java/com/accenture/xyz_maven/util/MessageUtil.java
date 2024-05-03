package com.accenture.xyz_maven.util;

import java.util.ResourceBundle;

public class MessageUtil 
{
    private static final String MESSAGE_BUNDLE_NAME = "messages";

    //Retrieves a message from ResourceBundle based on a key
    public static String getMessage(String key)
    {
        ResourceBundle rBundle = ResourceBundle.getBundle(MESSAGE_BUNDLE_NAME);
        return rBundle.getString(key);
    }


}
