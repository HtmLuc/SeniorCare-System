package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.BusinessException;
import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.PersonModel;
import com.htmluc.SeniorCare_System.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService
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

        int firstDigit = 11 - (aux % 11);
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

        int secondDigit = 11 - (aux % 11);
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

    @Transactional
    public PersonModel createPerson(PersonModel person)
    {
        if (!isCpfValid(person.getCpf()))
        {
            throw new BusinessException("CPF inválido");
        }

        if (cpfExists(person.getCpf()))
        {
            throw new BusinessException("CPF já cadastrado");
        }

        if (person.getDateBirth() == null)
        {
            throw new BusinessException("Data de nascimento é obrigatória");
        }

        return personRepository.save(person);
    }

    public Optional<PersonModel> findById(Long id)
    {
        return personRepository.findById(id);
    }

    public List<PersonModel> findAll()
    {
        return personRepository.findAll();
    }

    @Transactional
    public PersonModel updatePerson(Long id, PersonModel updatedPerson)
    {
        PersonModel existing = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        if (!existing.getCpf().equals(updatedPerson.getCpf()))
        {
            throw new BusinessException("Não é permitido alterar o CPF");
        }

        existing.setName(updatedPerson.getName());
        existing.setDateBirth(updatedPerson.getDateBirth());
        existing.setEmail(updatedPerson.getEmail());
        existing.setPhoneNumber(updatedPerson.getPhoneNumber());

        return personRepository.save(existing);
    }

    @Transactional
    public void deletePerson(Long id)
    {
        if (!personRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }

        personRepository.deleteById(id);
    }
}