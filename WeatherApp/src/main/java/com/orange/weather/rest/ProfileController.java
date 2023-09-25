package com.orange.weather.rest;

import com.orange.weather.DTO.UpdateUserDTO;
import com.orange.weather.DTO.UserDTO;
import com.orange.weather.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/myprofile")
public class ProfileController {

   private ProfileService profileService;

   public ProfileController (ProfileService profileService){
       this.profileService = profileService;
   }

    @GetMapping
    public ResponseEntity<UserDTO> viewProfile(Principal principal){
        UserDTO userInfo = profileService.getProfile(principal.getName());
        return ResponseEntity.status(HttpStatus.FOUND).body(userInfo);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateProfile(Principal principal, @RequestBody UpdateUserDTO request){
        UserDTO userDTO = profileService.updateProfile(request, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
