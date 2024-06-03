package com.luciorim.socnetserver.controllers;

import com.luciorim.socnetserver.dtos.MessageDto;
import com.luciorim.socnetserver.services.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageDto>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PostMapping
    public ResponseEntity<Void> createMessage(@Valid @RequestBody MessageDto messageDto){
        messageService.createMessage(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id){
        messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMessage(@PathVariable Long id, @Valid @RequestBody MessageDto messageDto){
        messageService.updateMessage(id, messageDto);
        return ResponseEntity.ok().build();
    }


}
