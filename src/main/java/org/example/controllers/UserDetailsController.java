package org.example.controllers;


import org.example.models.UserDetailsDTO;
import org.example.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user-details")
public class UserDetailsController {
    private static final String ID = "/{id}";

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(ID)
    public ResponseEntity<UserDetailsDTO> getDetails(@PathVariable("id") UUID id){
        return ResponseEntity.ok(userDetailsService.getUserDetails(id));
    }

    @PutMapping(ID)
    public ResponseEntity<UserDetailsDTO> updateDetails(
            @PathVariable("id") UUID id,
            @RequestBody UserDetailsDTO user
    ){
        return ResponseEntity.ok(userDetailsService.updateUserDetails(id, user));
    }
}
