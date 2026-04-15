package com.htmluc.SeniorCare_System.exception.person;

public class InvalidCpfException extends RuntimeException
{
    public InvalidCpfException()
    {
        super("CPF inválido!");
    }
}