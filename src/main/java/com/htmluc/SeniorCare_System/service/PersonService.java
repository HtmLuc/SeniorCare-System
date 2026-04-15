package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.model.PersonModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService
{
    @Transactional
    public PersonModel update(PersonModel personModel)
    {
        PersonModel personUpdate = new PersonModel();
        personUpdate.setName(personModel.getName());
        personUpdate.setEmail(personModel.getEmail());
        personUpdate.setPhoneNumber(personModel.getPhoneNumber());
        return personUpdate;
    }
}
