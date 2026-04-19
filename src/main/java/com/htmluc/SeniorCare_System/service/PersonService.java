package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.PersonModel;
import com.htmluc.SeniorCare_System.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonService
{
    private final PersonRepository personRepository;

    @Transactional
    public PersonModel update(Long id, PersonModel personModel)
    {
        PersonModel personUpdate = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));

        personUpdate.setName(personModel.getName());
        personUpdate.setEmail(personModel.getEmail());
        personUpdate.setPhoneNumber(personModel.getPhoneNumber());

        return personRepository.save(personUpdate);
    }
}
