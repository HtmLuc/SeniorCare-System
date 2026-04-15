package com.htmluc.SeniorCare_System.util;

import com.htmluc.SeniorCare_System.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CpfUtil
{
    @Autowired
    private PersonRepository personRepository;

    public boolean isCpfValid(String cpf)
    {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11)
        {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}"))
        {
            return false;
        }

        int aux = 0;

        for (int i = 0; i < 9; i++)
        {
            int digit = Character.getNumericValue(cpf.charAt(i));
            aux += digit * (10 - i);
        }

        int firstDigit = (aux * 10) % 11;

        if (firstDigit >= 10)
        {
            firstDigit = 0;
        }

        if (firstDigit != Character.getNumericValue(cpf.charAt(9)))
        {
            return false;
        }

        aux = 0;

        for (int i = 0; i < 10; i++)
        {
            int digit = Character.getNumericValue(cpf.charAt(i));
            aux += digit * (11 - i);
        }

        int secondDigit = (aux * 10) % 11;

        if (secondDigit >= 10)
        {
            secondDigit = 0;
        }

        return secondDigit == Character.getNumericValue(cpf.charAt(10));
    }

    public boolean cpfExists(String cpf)
    {
        return personRepository.existsByCpf(cpf);
    }
}
