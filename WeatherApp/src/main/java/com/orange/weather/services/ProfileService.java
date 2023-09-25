package com.orange.weather.services;

import com.orange.weather.DTO.UpdateUserDTO;
import com.orange.weather.DTO.UserDTO;
import com.orange.weather.entity.User;
import com.orange.weather.exception.InvalidDetailsException;
import com.orange.weather.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private UserDetailsService userDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    public ProfileService(UserDetailsServiceImpl userDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // method for getting a user's profile details
    public UserDTO getProfile(String username){
        logger.info("Method getProfile in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: String " + username);
        UserDetails currDetails = userDetailsService.loadUserByUsername(username);
        String email = currDetails.getUsername();
        String name = ((User) currDetails).getName();
        String mobile = ((User) currDetails).getMobileNumber();
        return new UserDTO(name, email, mobile);
    }

    // method for updating a user's account details
    public UserDTO updateProfile(UpdateUserDTO request, String email){
        logger.info("Method updateProfile in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: UpdateUserDTO " + request.toString() + ", String " + email);
        for (char c : request.getName().toCharArray()){
            if (!(Character.isAlphabetic(c)) && !(Character.isWhitespace(c))){
                throw new InvalidDetailsException("Name field cannot contain numbers or special characters!");
            }
        }
        for (char c : request.getMobile().toCharArray()){
            if (!(Character.isDigit(c))){
                throw new InvalidDetailsException("Mobile number can only contain numbers!");
            }
        }
        User curr = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not authenticated"));
        curr.setName(request.getName());
        curr.setPassword(passwordEncoder.encode(request.getPassword()));
        curr.setMobileNumber(request.getMobile());
        User updated = userRepository.save(curr);
        return new UserDTO(updated.getName(), updated.getEmail(), updated.getMobileNumber());
    }
}
