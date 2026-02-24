package com.htmluc.SeniorCare_System.exception;

public class InvalidCpfException extends RuntimeException
{
    public InvalidCpfException()
    {
        super("CPF inválido!");
    }
}