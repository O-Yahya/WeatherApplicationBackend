package com.orange.weather.services;

import com.orange.weather.DTO.UserDTO;
import com.orange.weather.entity.Role;
import com.orange.weather.entity.User;
import com.orange.weather.exception.InvalidDetailsException;
import com.orange.weather.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    public AdminService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // method for finding all admins in system
    public List<UserDTO> findAll(){
        logger.info("Method findAll in AdminService called");
        logger.debug("Parameters: None");
        List<UserDTO> adminsInfo = new ArrayList<>();
        List<User> admins = new ArrayList<>();
        if (userRepository.findByRole(Role.ADMIN) != null){
            admins = userRepository.findByRole(Role.ADMIN);
        }
        else {
            throw new RuntimeException("No admins registered in system.");
        }

        for (User admin : admins){
            adminsInfo.add(new UserDTO(admin.getName(), admin.getEmail(), admin.getMobileNumber()));
        }
        return adminsInfo;
    }

    // method for deleting an admin based on the given email
    public void deleteAdmin(String email){
        logger.info("Method deleteAdmin in AdminService called");
        logger.debug("Parameters: String " + email);
        User toDelete = userRepository.findByEmail(email).orElseThrow(() -> new InvalidDetailsException("No user with email - " + email));
        userRepository.delete(toDelete);
    }

}
