package com.example.track_expense.exceptions;

public class WrongTimePeriodException extends Exception{
    public WrongTimePeriodException(String message){
        super(message);
    }
}
