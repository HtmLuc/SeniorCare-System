package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.repository.MonitoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitoringService
{
    private MonitoringRepository monitoringRepository;

    @Transactional(readOnly = true)
    public Page<MonitoringModel> listAll(Pageable pageable)
    {
        return monitoringRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<MonitoringModel> findById(Long id)
    {
        return monitoringRepository.findById(id);
    }

    @Transactional
    public MonitoringModel update(Long id, MonitoringModel monitoringModel)
    {
        MonitoringModel info = monitoringRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum monitoramento."));

        info.setName(monitoringModel.getName());
        info.setBloodSugar(monitoringModel.getBloodSugar());
        info.setBloodPressure(monitoringModel.getBloodPressure());
        info.setHeartRate(monitoringModel.getHeartRate());
        info.setRespiratoryRate(monitoringModel.getRespiratoryRate());
        info.setSaturation(monitoringModel.getSaturation());
        info.setTemperature(monitoringModel.getTemperature());

        return monitoringRepository.save(info);
    }

    @Transactional
    public void delete(Long id)
    {
        monitoringRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum monitoramento."));
        monitoringRepository.deleteById(id);
    }
}
