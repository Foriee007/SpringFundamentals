package com.dictionaryapp.service;

import com.dictionaryapp.model.dto.UserRegisterDto;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public boolean register(UserRegisterDto data){
        if (!data.getPassword().equals(data.getConfirmPassword())){
            return false;
        }
        Optional<User> byEmail=  userRepository.findByEmail(data.getEmail());
        if(byEmail.isPresent()){
            return false;
        }
        User mapped = modelMapper.map(data, User.class);
        userRepository.save(mapped);

        return true;
    }
}
