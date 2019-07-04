package com.vitorgsevero.kanbantool.KanbanTool2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.User;
import com.vitorgsevero.kanbantool.KanbanTool2.exceptions.UsernameAlreadyExistsException;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.UserRepository;

@Service
public class UserService {

	

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser (User newUser) {
		
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			//Username has to be unique 
			newUser.setUsername(newUser.getUsername());
			//Don't persisting the password
			newUser.setConfirmPassword("");
			return userRepository.save(newUser);
			
		}catch(Exception error) {
			throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists!");
		}

	}
	
}
