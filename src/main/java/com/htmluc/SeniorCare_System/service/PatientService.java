package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.MedicineModel;
import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.model.PatientModel;
import com.htmluc.SeniorCare_System.model.PersonModel;
import com.htmluc.SeniorCare_System.repository.MedicineRepository;
import com.htmluc.SeniorCare_System.repository.MonitoringRepository;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import com.htmluc.SeniorCare_System.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService
{
    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;

    @Transactional(readOnly = true)
    public Page<PatientModel> listAll(Pageable pageable)
    {
        Page<PatientModel> patients = patientRepository.findAll(pageable);
        return patients;
    }

    @Transactional(readOnly = true)
    public Optional<PatientModel> findById(Long id)
    {
        return patientRepository.findById(id);
    }

    @Transactional
    public PatientModel update(Long id, PatientModel patientModel)
    {
        PatientModel patientUpdate = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        patientUpdate.setGender(patientModel.getGender());
        patientUpdate.setDegreeDependence(patientModel.getDegreeDependence());
        patientUpdate.setObservations(patientModel.getObservations());

        if (patientModel.getPerson() != null)
        {
            patientUpdate.setPerson(personService.update(id, patientModel.getPerson()));
        }

        return patientRepository.save(patientUpdate);
    }

    @Transactional
    public PatientModel create(PatientModel patientModel)
    {
        //Validar CPF
        PersonModel person = personRepository.save(patientModel.getPerson());
        patientModel.setPerson(person);
        return patientRepository.save(patientModel);
    }

    @Transactional
    public PatientModel createMedicineByPatient(Long id, MedicineModel medicineModel)
    {
        PatientModel patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado."));
        if (patient.getMedicines() == null)
        {
            patient.setMedicines(new ArrayList<>());
        }
        patient.getMedicines().add(medicineModel);
        patientRepository.save(patient);
        return patient;
    }

    @Transactional
    public PatientModel createMonitoringByPatient(Long id, MonitoringModel monitoringModel)
    {
        PatientModel patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado."));
        if (patient.getMonitorings() == null)
        {
            patient.setMonitorings(new ArrayList<>());
        }
        patient.getMonitorings().add(monitoringModel);
        patientRepository.save(patient);
        return patient;
    }

    @Transactional
    public void delete(Long id)
    {
        if (!patientRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Paciente não encontrada");
        }

        patientRepository.deleteById(id);
    }
}
