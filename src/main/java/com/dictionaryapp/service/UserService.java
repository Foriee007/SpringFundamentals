package com.dictionaryapp.service;

import com.dictionaryapp.model.dto.UserRegisterDto;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private final ModelMapper modelMapper;
    private  final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(UserRegisterDto data){
        if (!data.getPassword().equals(data.getConfirmPassword())){
            return false;
        }
        Optional<User> byEmail=  userRepository.findByEmail(data.getEmail());
        if(byEmail.isPresent()){
            return false;
        }
        Optional<User> byUsername=  userRepository.findByUserName(data.getUsername());
        if(byUsername.isPresent()){
            return false;
        }
        User mapped = modelMapper.map(data, User.class);
        mapped.setPassword(passwordEncoder.encode(mapped.getPassword()));
        userRepository.save(mapped);

        return true;
    }
}
