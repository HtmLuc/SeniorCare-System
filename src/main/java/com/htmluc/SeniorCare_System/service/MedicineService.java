package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.BusinessException;
import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.MedicineModel;
import com.htmluc.SeniorCare_System.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MedicineService
{
    private final MedicineRepository medicineRepository;

    @Transactional(readOnly = true)
    public Page<MedicineModel> listAll(Pageable pageable)
    {
        return medicineRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public MedicineModel findById(Long id)
    {
        return medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado medicamento."));
    }

    @Transactional
    public MedicineModel update(Long id, MedicineModel medicineModel)
    {
        MedicineModel info = medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado medicamento."));

        info.setName(medicineModel.getName());
        info.setAcquisition(medicineModel.getAcquisition());
        info.setDosage(medicineModel.getDosage());
        info.setFrequency(medicineModel.getFrequency());

        medicineRepository.save(info);

        return info;
    }

    @Transactional
    public MedicineModel create(MedicineModel medicineModel)
    {
        return medicineRepository.save(medicineModel);
    }

    @Transactional
    public void delete(Long id)
    {
        MedicineModel medicine = medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado."));
        if (medicine.getPatients() != null && !medicine.getPatients().isEmpty())
        {
            throw new BusinessException("Existem pacientes associados a este medicamento.");
        }
        medicineRepository.deleteById(id);
    }
}
