package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.BusinessException;
import com.htmluc.SeniorCare_System.exception.person.ConflictCpfException;
import com.htmluc.SeniorCare_System.exception.person.InvalidCpfException;
import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.UserModel;
import com.htmluc.SeniorCare_System.repository.UserRepository;
import com.htmluc.SeniorCare_System.util.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService
{
    @Autowired
    private CpfUtil cpfUtil;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Page<UserModel> listAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public UserModel create(UserModel user)
    {
        if (!cpfUtil.isCpfValid(user.getPerson().getCpf()))
        {
            throw new InvalidCpfException();
        }

        if (cpfUtil.cpfExists(user.getPerson().getCpf()))
        {
            throw new ConflictCpfException("CPF já cadastrado");
        }

        return userRepository.save(user);
    }

    @Transactional
    public UserModel update(Long id, UserModel user)
    {
        UserModel existing = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        if (!existing.getPerson().getCpf().equals(user.getPerson().getCpf()))
        {
            throw new BusinessException("Não é permitido alterar os dados");
        }

        existing.setFunction(user.getFunction());
        existing.getPerson().setName(user.getPerson().getName());
        existing.getPerson().setDateBirth(user.getPerson().getDateBirth());
        existing.getPerson().setEmail(user.getPerson().getEmail());
        existing.getPerson().setPhoneNumber(user.getPerson().getPhoneNumber());

        return userRepository.save(existing);
    }

    @Transactional
    public void delete(Long id)
    {
        if (!userRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }

        userRepository.deleteById(id);
    }
}