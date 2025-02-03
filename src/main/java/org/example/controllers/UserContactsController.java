package org.example.controllers;


import org.example.models.UserContactsDTO;
import org.example.services.UserContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/user-contacts")
public class UserContactsController {

    private static final String ID = "/{id}";

    @Autowired
    private UserContactsService userContactsService;

    @PostMapping
    public ResponseEntity<UserContactsDTO> createUser(@RequestBody UserContactsDTO userContactsDTO) {
        UserContactsDTO createdUser = userContactsService.createUser(userContactsDTO);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdUser.getId())
                        .toUri()
        ).body(createdUser);
    }

    @GetMapping(ID)
    public ResponseEntity<UserContactsDTO> getUser(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userContactsService.getUser(id));
    }

    @PutMapping(ID)
    public ResponseEntity<UserContactsDTO> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody UserContactsDTO user
    ) {
        return ResponseEntity.ok(userContactsService.updateUser(id, user));
    }

    @DeleteMapping(ID)
    public void deleteUser(@PathVariable("id") UUID id) {
        userContactsService.deleteUser(id);
    }
}
