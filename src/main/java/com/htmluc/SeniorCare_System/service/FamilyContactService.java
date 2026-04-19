package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.FamilyContactModel;
import com.htmluc.SeniorCare_System.repository.FamilyContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FamilyContactService
{
    private final FamilyContactRepository familyContactRepository;

    @Transactional(readOnly = true)
    public Page<FamilyContactModel> listAll(Pageable pageable)
    {
        return familyContactRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public FamilyContactModel findById(Long id)
    {
        return familyContactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Familiar não encontrado."));
    }

    @Transactional
    public FamilyContactModel update(Long id, FamilyContactModel familyContactModel)
    {
        FamilyContactModel familyContact = familyContactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Familiar não encontrado."));

        familyContact.setCep(familyContactModel.getCep());
        familyContact.setUf(familyContactModel.getUf());
        familyContact.setCity(familyContactModel.getCity());
        familyContact.setNeighborhood(familyContactModel.getNeighborhood());
        familyContact.setHouseNumber(familyContactModel.getHouseNumber());
        familyContact.setRoad(familyContactModel.getRoad());
        familyContact.setRelationship(familyContactModel.getRelationship());

        familyContactRepository.save(familyContact);

        return familyContact;
    }

    @Transactional
    public FamilyContactModel create(FamilyContactModel familyContactModel)
    {
        return familyContactRepository.save(familyContactModel);
    }

    @Transactional
    public void delete(Long id)
    {
        FamilyContactModel famiyContact = familyContactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contato familiar não encontrado."));
        familyContactRepository.delete(famiyContact);
    }
}
