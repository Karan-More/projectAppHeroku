package com.capstone.kanbantool.services;

import com.capstone.kanbantool.domain.User;
import com.capstone.kanbantool.exception.UsernameExistsInDatabaseException;
import com.capstone.kanbantool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){

            try{
                newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
                newUser.setConfirmPassword(bCryptPasswordEncoder.encode(newUser.getConfirmPassword()));
                newUser.setUsername(newUser.getUsername());
                return userRepository.save(newUser);
            }catch (Exception ex){
                    throw new UsernameExistsInDatabaseException("Username '"+newUser.getUsername()+"' already registered!!");
            }


    }
}
