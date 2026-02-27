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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class PatientService
{
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Transactional
    public Page<PatientModel> listAll(Pageable pageable)
    {
        Page<PatientModel> patients = patientRepository.findAll(pageable);
        return patients;
    }

    @Transactional
    public PatientModel update(Long id, PatientModel patientModel)
    {
        PatientModel patientUpdate = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));

        patientUpdate.setGender(patientModel.getGender());
        patientUpdate.setDegree_dependence(patientModel.getDegree_dependence());
        patientUpdate.setObservations(patientModel.getObservations());

        if (patientModel.getPerson() != null) {
            patientUpdate.setPerson(personService.update(patientModel.getPerson()));
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
    public MedicineModel createMedicineByPatient(MedicineModel medicineModel, PatientModel patientModel)
    {
        //Validar se o medicamento já existe
        MedicineModel medicine = medicineRepository.save((medicineModel));

        if (patientModel.getMedicines() == null)
        {
            patientModel.setMedicines(new ArrayList<>());
        }

        patientModel.getMedicines().add(medicine);
        patientRepository.save(patientModel);

        return medicine;
    }

    @Transactional
    public MonitoringModel createMonitoringByPatient(PatientModel patient, MonitoringModel monitoringModel)
    {
        monitoringModel.setPatient(patient);
        return this.monitoringRepository.save(monitoringModel);
    }

    @Transactional
    public void delete(Long id)
    {
        if (!this.patientRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Paciente não encontrada");
        }

        patientRepository.deleteById(id);
    }
}
