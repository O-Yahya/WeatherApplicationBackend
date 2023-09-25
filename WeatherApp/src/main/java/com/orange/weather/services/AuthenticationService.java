package com.orange.weather.services;

import com.orange.weather.DTO.AuthenticationRequest;
import com.orange.weather.DTO.AuthenticationResponse;
import com.orange.weather.DTO.RegisterRequest;
import com.orange.weather.config.JwtService;
import com.orange.weather.entity.Role;
import com.orange.weather.entity.User;
import com.orange.weather.exception.IncorrectCredentialsException;
import com.orange.weather.exception.InvalidDetailsException;
import com.orange.weather.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class AuthenticationService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // method for registering a new user
    public AuthenticationResponse register (RegisterRequest request){
        logger.info("Method register in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: RegisterRequest " + request.toString());
        if (((request.getName() == null) || request.getName().isEmpty()) || ((request.getEmail() == null || request.getEmail().isEmpty()) || ((request.getPassword() == null) || request.getPassword().isEmpty()))){
            logger.error("InvalidDetailsException: Missing fields.");
            throw new InvalidDetailsException("All user registration fields must be filled!");
        }
        if (userRepo.findByEmail(request.getEmail()).isPresent()){
            logger.error("InvalidDetailsException: User already exists.");
            throw new InvalidDetailsException("User with email - " + request.getEmail() + " already exists!");
        }
        for (char c : request.getName().toCharArray()){
            if (!(Character.isAlphabetic(c)) && !(Character.isWhitespace(c))){
                logger.error("InvalidDetailsException: Invalid input.");
                throw new InvalidDetailsException("Name field cannot contain numbers or special characters!");
            }
        }
        for (char c : request.getMobileNumber().toCharArray()){
            if (!(Character.isDigit(c))){
                logger.error("InvalidDetailsException: Invalid input.");
                throw new InvalidDetailsException("Mobile number can only contain numbers!");
            }
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // method for registering a new admin
    public AuthenticationResponse registerAdmin (RegisterRequest request){
        logger.info("Method registerAdmin in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: RegisterRequest " + request.toString());
        if ((request.getName() == null) || (request.getEmail() == null || (request.getPassword() == null))){
            logger.error("InvalidDetailsException: Missing fields.");
            throw new InvalidDetailsException("All admin registration fields must be filled!");
        }
        if (userRepo.findByEmail(request.getEmail()).isPresent()){
            logger.error("InvalidDetailsException: User already exists.");
            throw new InvalidDetailsException("User with email - " + request.getEmail() + " already exists!");
        }
        for (char c : request.getName().toCharArray()){
            if (!(Character.isAlphabetic(c)) && !(Character.isWhitespace(c))){
                logger.error("InvalidDetailsException: Invalid input.");
                throw new InvalidDetailsException("Name field cannot contain numbers or special characters!");
            }
        }
        for (char c : request.getMobileNumber().toCharArray()){
            if (!(Character.isDigit(c))){
                logger.error("InvalidDetailsException: Invalid input.");
                throw new InvalidDetailsException("Mobile number can only contain numbers!");
            }
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // method for authenticating an already created user and returning a JWT to be used for authenticating other requests
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        logger.info("Method authenticate in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: AuthenticationRequest " + request.toString());
        if ((request.getEmail() == null) || (request.getPassword() == null)){
            logger.error("InvalidDetailsException: Missing fields.");
            throw new InvalidDetailsException("User must enter both email and password to login!");
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }
        catch (Exception e){
            logger.error("IncorrectCredentialsException: Incorrect login details entered.");
            throw new IncorrectCredentialsException("Incorrect email or password");
        }

        var user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidDetailsException("Could not find user with email - " + request.getEmail()));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
