package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.model.PatientModel;
import com.htmluc.SeniorCare_System.repository.MonitoringRepository;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.monitor.Monitor;

@Service
public class PatientService
{
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

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
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }

        patientRepository.deleteById(id);
    }
}
