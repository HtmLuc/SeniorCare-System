package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.BusinessException;
import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.PersonModel;
import com.htmluc.SeniorCare_System.repository.PersonRepository;
import com.htmluc.SeniorCare_System.util.CpfUtil;
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

    @Autowired
    private CpfUtil cpfUtil;

    @Transactional
    public PersonModel createPerson(PersonModel person)
    {
        if (!cpfUtil.isCpfValid(person.getCpf()))
        {
            throw new BusinessException("CPF inválido");
        }

        if (cpfUtil.cpfExists(person.getCpf()))
        {
            throw new BusinessException("CPF já cadastrado");
        }

        return personRepository.save(person);
    }

    @Transactional
    public PersonModel updatePerson(Long id, PersonModel updatedPerson)
    {
        PersonModel existing = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        if (!existing.getCpf().equals(updatedPerson.getCpf()))
        {
            throw new BusinessException("Não é permitido alterar os dados");
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