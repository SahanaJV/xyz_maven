package com.accenture.xyz_maven.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidations 
{
    //Validates the given password and returns a boolean value 
    public static boolean passwordValidation(String password)
    {
        //no blank space
        //atleast 1 number, 1 upper and 1 lower case, 1 special char
        //length = 8 to 20
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //Validates the given username and returns a boolean value
    public static boolean usernameValidation(String username)
    {
        //Should always start with upper/lower case alphabet
        //only _ allowed
        //length from 6 to 30 
        String regex = "^(?=.*[A-Z])[a-zA-Z0-9]{6,30}$";
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    } 
}
