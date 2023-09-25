package com.orange.weather.rest;

import com.orange.weather.DTO.AuthenticationResponse;
import com.orange.weather.DTO.RegisterRequest;
import com.orange.weather.DTO.UserDTO;
import com.orange.weather.services.AdminService;
import com.orange.weather.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authService;
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAdmins(){
        List<UserDTO> adminsInfo = adminService.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(adminsInfo);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String email){
        adminService.deleteAdmin(email);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted admin with email - " + email);
    }
}
