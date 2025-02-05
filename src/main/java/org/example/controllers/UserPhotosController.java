package org.example.controllers;


import org.example.models.UserPhotosDTO;
import org.example.services.UserPhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/user-photos")
public class UserPhotosController {
    @Autowired
    private UserPhotosService service;
    private static final String ID = "/{id}";

    @PostMapping()
    public ResponseEntity<UserPhotosDTO> createPhoto(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("id") UUID userId) {
        UserPhotosDTO savedPhoto = service.saveUserPhoto(file, userId);

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedPhoto.getId())
                        .toUri()
        ).body(savedPhoto);
    }

    @GetMapping(ID)
    public ResponseEntity<UserPhotosDTO> getPhotoInfo(@PathVariable("id") UUID photoId) {
        return ResponseEntity.ok(service.getUserPhotoInto(photoId));
    }

    @GetMapping(ID + "/download")
    public ResponseEntity<Void> downloadPhoto(@PathVariable("id") UUID photoId) {
        return ResponseEntity.status(307)
                .contentType(MediaType.TEXT_PLAIN)
                .header("" +
                        "location", service.getPhotoLink(photoId))
                .build();
    }

    @PutMapping("/update-photo")
    public ResponseEntity<UserPhotosDTO> updatePhoto(@RequestParam("file") MultipartFile photoFile,
                                                     @RequestParam("id") UUID userId) {
        UserPhotosDTO updated = service.updateUserPhoto(photoFile, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(ID)
    public void deletePhoto(@PathVariable("id") UUID photoId) {
        service.deleteUserPhoto(photoId);
    }
}
