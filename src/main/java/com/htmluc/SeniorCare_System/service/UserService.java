package com.htmluc.SeniorCare_System.service;

import com.htmluc.SeniorCare_System.exception.person.ConflictCpfException;
import com.htmluc.SeniorCare_System.exception.person.InvalidCpfException;
import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.UserModel;
import com.htmluc.SeniorCare_System.repository.UserRepository;
import com.htmluc.SeniorCare_System.util.CpfUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private CpfUtil cpfUtil;
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserModel> listAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public UserModel findById(Long id)
    {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
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
    public void delete(Long id)
    {
        if (!userRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }

        userRepository.deleteById(id);
    }
}