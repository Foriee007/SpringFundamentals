package com.dictionaryapp.service;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.UserLoginDto;
import com.dictionaryapp.model.dto.UserRegisterDto;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private final ModelMapper modelMapper;
    private  final PasswordEncoder passwordEncoder;
    private final UserSession userSession;


    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }
    // Registration
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

    //Login logic
    public boolean login(UserLoginDto data){
        Optional<User> byUsername =  userRepository.findByUserName(data.getUsername());

        if (byUsername.isEmpty()){
            return  false;
        }
        User user = byUsername.get();
        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            return  false;
        }
        //Add to session
        userSession.login(user);

        return  true;
    }


    public void logout() {
        userSession.logout();
    }

    public void removeAllWords() {
        List<User> all = userRepository.findAll();
        all.stream().forEach(user -> {user.getAddedWords().clear();
            userRepository.save(user);
        });
    }
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
