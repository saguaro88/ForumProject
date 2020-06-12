package com.javaee.projectFroum.projectForum.exceptions;

public class WrongUsernameException extends Exception {
    public WrongUsernameException(String errorMessage){
        super(errorMessage);
    }
}
